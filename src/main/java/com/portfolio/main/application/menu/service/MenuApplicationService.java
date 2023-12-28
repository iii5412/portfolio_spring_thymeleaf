package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.menu.dto.CreateMenu;
import com.portfolio.main.application.menu.dto.EditMenu;
import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.application.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.application.menu.exception.UpperMenuNotFoundException;
import com.portfolio.main.application.menurole.dto.SaveMenuRole;
import com.portfolio.main.application.menurole.service.MenuRoleApplicationService;
import com.portfolio.main.application.program.service.ProgramApplicationService;
import com.portfolio.main.domain.service.role.RoleService;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.account.exception.RoleNotFoundException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.exception.MenuNotFoundException;
import com.portfolio.main.domain.service.menu.MenuService;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
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
public class MenuApplicationService {
    private final MenuService menuService;
    private final RoleService roleService;
    private final ProgramApplicationService programApplicationService;
    private final MenuRoleApplicationService menuRoleApplicationService;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public MenuApplicationService(
            MenuService menuService
            , RoleService roleService
            , MenuRoleApplicationService menuRoleApplicationService
            , ProgramApplicationService programApplicationService
            , MyUserDetailsService userDetailsService) {
        this.menuService = menuService;
        this.roleService = roleService;
        this.menuRoleApplicationService = menuRoleApplicationService;
        this.userDetailsService = userDetailsService;
        this.programApplicationService = programApplicationService;
    }

    public MenuDto findById(Long id) {
        final Menu menu = menuService.findById(id);
        return new MenuDto(menu);
    }

    /**
     * 사용자 권한을 가져와 소속된 메뉴를 조회한다.
     *
     * @return
     */
    public List<MenuDto> selectAllMenusByUserRole() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final RoleCode highestRoleCode = RoleCode.getHighestAuthority(authentication.getAuthorities());
        final List<Menu> allMenus = menuService.selectMenuByRoleCode(highestRoleCode);

        return filterSubMenusById(allMenus);
    }

    public PageResult<MenuDto> selectMenuWithPageable(SearchMenu searchMenu) {
        final PageRequest pageable = PageRequest.of(searchMenu.getPage() - 1, searchMenu.getSize(), searchMenu.getSort());
        final PageResult<Menu> menuPageResult = menuService.selectMenuWithPageable(searchMenu, pageable);

        return PageResult.<MenuDto>builder()
                .result(menuPageResult.getResult().stream().map(MenuDto::new).toList())
                .totalCount(menuPageResult.getTotalCount())
                .page(menuPageResult.getPage())
                .size(menuPageResult.getSize())
                .build();
    }

    public List<MenuDto> selectMenu(SearchMenu searchMenu) {
        final List<Menu> menus = menuService.selectMenu();
        final List<Menu> filterMenus = filterBySearchMenu(menus, searchMenu);
        return filterSubMenusById(filterMenus);
    }

    public List<MenuDto> selectFolrderMenus() {
        final List<Menu> menus = menuService.selectMenu();
        return menus.stream().filter(Menu::isFolder).map(MenuDto::new).toList();
    }

    public Long saveMenu(CreateMenu createMenu) throws UpperMenuNotFoundException, RoleNotFoundException {
        final User createUser = userDetailsService.findUserByLoginId(createMenu.getCreateUserLoginId());
        final Menu newMenu = new Menu(createMenu.getMenuName(), createMenu.getMenuType(), createMenu.getOrderNum(), createUser);

        if (createMenu.getUpperId() != null) {
            final Menu upperMenu = menuService.findById(createMenu.getUpperId());
            newMenu.setUpperMenu(upperMenu);
        }

        Menu savedMenu = menuService.save(newMenu);

        //Role 조회
        RoleCode roleCode = RoleCode.valueOf(createMenu.getRoleCode());
        final Role findRole = roleService.findByRoleCode(roleCode);

        menuRoleApplicationService.save(new SaveMenuRole(savedMenu.getId(), findRole.getId()));

        return savedMenu.getId();
    }

    public void deleteMenu(Long menuId) throws MenuNotFoundException, CannotDeleteMenuWithSubmenusException {
        final Menu targetMenu = menuService.findById(menuId);
        if (!targetMenu.getSubMenus().isEmpty()) {
            throw new CannotDeleteMenuWithSubmenusException();
        }

        menuRoleApplicationService.deleteByMenuId(menuId);
        menuService.deleteById(menuId);
    }

    public Long edit(EditMenu editMenu) {
        final Long menuId = editMenu.getId();
        final Menu targetMenu = menuService.findById(menuId);

        Menu upperMenu = null;
        if (editMenu.getUpperId() != null) {
            upperMenu = menuService.findById(editMenu.getUpperId());
        }

        Program program = null;
        if (editMenu.getProgramId() != null) {
            program = programApplicationService.findById(editMenu.getProgramId());
        }

        final User editUser = userDetailsService.findUserByLoginId(editMenu.getEditUserLoginId());

        targetMenu.edit(editMenu, upperMenu, program, editUser);

        final RoleCode topRoleCode = menuRoleApplicationService.findTopRoleCodeByMenu(targetMenu.getId());
        if (StringUtils.hasText(editMenu.getRoleCode())) {
            final RoleCode editRoleCode = RoleCode.valueOf(editMenu.getRoleCode());
            if (!editRoleCode.equals(topRoleCode)) {
                menuRoleApplicationService.changeRole(new MenuDto(targetMenu), editRoleCode);
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

    private List<Menu> filterBySearchMenu(List<Menu> menus, SearchMenu searchMenu) {
        return menus.stream()
                .filter(m -> {
                    if(searchMenu.getId() != null) {
                        return m.getId() == searchMenu.getId();
                    } else {
                        return true;
                    }
                })
                .filter(m -> {
                    if(searchMenu.getMenuName() != null){
                        return m.getMenuName().contains(searchMenu.getMenuName());
                    } else {
                        return true;
                    }
                })
                .filter(m -> {
                    if (searchMenu.getRoleCode() != null) {
                        final List<MenuRole> menuRoles = menuRoleApplicationService.findByMenu(m);
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

}
