package com.portfolio.main.menu.service;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.role.exception.NotFoundRoleException;
import com.portfolio.main.account.role.service.RoleCode;
import com.portfolio.main.account.role.service.RoleService;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.MenuRole;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.menu.CreateMenu;
import com.portfolio.main.menu.dto.menu.EditMenu;
import com.portfolio.main.menu.dto.menu.MenuDto;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.menu.exception.MenuNotFoundException;
import com.portfolio.main.menu.exception.NotFoundMenuRoleException;
import com.portfolio.main.menu.exception.UpperMenuNotFoundException;
import com.portfolio.main.menu.repository.MenuMapper;
import com.portfolio.main.menu.repository.MenuRepository;
import com.portfolio.main.util.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final RoleService roleService;
    private final ProgramService programService;
    private final MenuRoleService menuRoleService;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public MenuService(
            MenuRepository menuRepository
            , MenuMapper menuMapper
            , RoleService roleService
            , MenuRoleService menuRoleService
            , ProgramService programService
            , MyUserDetailsService userDetailsService) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
        this.roleService = roleService;
        this.menuRoleService = menuRoleService;
        this.userDetailsService = userDetailsService;
        this.programService = programService;
    }

    public MenuDto findById(Long id) {
        final Menu menu = menuRepository.findById(id).orElseThrow(NotFoundMenuRoleException::new);
        return new MenuDto(menu);
    }

    /**
     * 사용자 권한을 가져와 소속된 메뉴를 조회한다.
     *
     * @return
     */
    public List<MenuDto> findAllMenusByUserRole() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final RoleCode highestRoleCode = RoleCode.getHighestAuthority(authentication.getAuthorities());
        final List<Menu> allMenus = menuRepository.selectMenuByRoleCode(highestRoleCode);

        return filterSubMenusById(allMenus);
    }

    public PageResult<MenuDto> selectMenuWithPageable(SearchMenu searchMenu) {
        final PageRequest pageable = PageRequest.of(searchMenu.getPage() - 1, searchMenu.getSize(), searchMenu.getSort());
        final PageResult<Menu> menuPageResult = menuRepository.selectMenuWithPageable(searchMenu, pageable);

        return PageResult.<MenuDto>builder()
                .result(menuPageResult.getResult().stream().map(MenuDto::new).toList())
                .totalCount(menuPageResult.getTotalCount())
                .page(menuPageResult.getPage())
                .size(menuPageResult.getSize())
                .build();
    }

    public List<MenuDto> selectMenu(SearchMenu searchMenu) {
        final List<Menu> menus = menuRepository.selectMenu();
        final List<Menu> filterMenus = filterBySearchMenu(menus, searchMenu);
        return filterSubMenusById(filterMenus);
    }

    private List<Menu> filterBySearchMenu(List<Menu> menus, SearchMenu searchMenu) {
        return menus.stream()
                .filter(m -> searchMenu.getId() != null && m.getId() == searchMenu.getId())
                .filter(m -> searchMenu.getMenuName() != null && m.getMenuName().contains(searchMenu.getMenuName()))
                .filter(m -> {
                    if (searchMenu.getRoleCode() != null) {
                        final List<MenuRole> menuRoles = menuRoleService.findByMenu(m);
                        if (menuRoles.isEmpty()) {
                            return false;
                        } else {
                            return menuRoles.stream()
                                    .anyMatch(mr -> mr.getRole().getRoleCode() == RoleCode.valueOf(searchMenu.getRoleCode()));
                        }
                    }

                    return true;
                }).toList();
    }


    public Long saveMenu(CreateMenu createMenu) throws UpperMenuNotFoundException, NotFoundRoleException {
        final User createUser = userDetailsService.findUserByLoginId(createMenu.getCreateUserLoginId());
        final Menu newMenu = new Menu(createMenu.getMenuName(), createMenu.getMenuType(), createMenu.getOrderNum(), createUser);

        if (createMenu.getUpperId() != null) {
            final Menu upperMenu = menuRepository.findById(createMenu.getUpperId()).orElseThrow(UpperMenuNotFoundException::new);
            newMenu.setUpperMenu(upperMenu);
        }

        Menu savedMenu = menuRepository.save(newMenu);

        //Role 조회
        RoleCode roleCode = RoleCode.valueOf(createMenu.getRoleCode());
        final Role findRole = roleService.findByRoleCode(roleCode);

        final MenuRole menuRole = new MenuRole(new MenuRole.MenuRoleId(savedMenu.getId(), findRole.getId()), savedMenu, findRole);

        menuRoleService.save(menuRole);

        return savedMenu.getId();
    }

    public void deleteMenu(Long menuId) throws MenuNotFoundException, CannotDeleteMenuWithSubmenusException {
        final Menu targetMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        if (!targetMenu.getSubMenus().isEmpty()) {
            throw new CannotDeleteMenuWithSubmenusException();
        }

        menuRoleService.deleteByMenuId(menuId);
        menuRepository.deleteById(menuId);
    }

    public Long edit(EditMenu editMenu) {
        final Long menuId = editMenu.getId();
        final Menu targetMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);

        Menu upperMenu = null;
        if (editMenu.getUpperId() != null) {
            upperMenu = menuRepository.findById(editMenu.getUpperId()).orElseThrow(MenuNotFoundException::new);
        }

        Program program = null;
        if (editMenu.getProgramId() != null) {
            program = programService.findById(editMenu.getProgramId());
        }

        final User editUser = userDetailsService.findUserByLoginId(editMenu.getEditUserLoginId());


        targetMenu.edit(editMenu, upperMenu, program, editUser);

        final RoleCode topRoleCode = menuRoleService.findTopRoleCodeByMenu(targetMenu);
        if (StringUtils.hasText(editMenu.getRoleCode())) {
            final RoleCode editRoleCode = RoleCode.valueOf(editMenu.getRoleCode());
            if (!editRoleCode.equals(topRoleCode)) {
                menuRoleService.changeRole(targetMenu, editRoleCode);
            }
        }

        return menuId;
    }

    /**
     * 상위 객체의 subMenus 중 조회된 하위 메뉴를 제외한 메뉴만 포함하여 다시 구성하여 반환한다.
     *
     * @param targetMenus
     * @return
     */
    private List<MenuDto> filterSubMenusById(List<Menu> targetMenus) {
        List<MenuDto> topMenus = new ArrayList<>();
        for (Menu menu : targetMenus) {
            menu.clearSubMenu();
            if (!menu.hasUpperMenu()) {
                topMenus.add(new MenuDto(menu));
            } else {
                topMenus.stream()
                        .filter(topMenuDto -> Objects.equals(topMenuDto.getId(), menu.getUpperMenu().getId()))
                        .forEach(topMenuDto -> topMenuDto.addSubMenu(menu));
            }
        }
        return topMenus;
    }

}
