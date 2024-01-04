package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.dto.MenuManageDto;
import com.portfolio.main.application.menu.dto.factory.MenuDtoFactory;
import com.portfolio.main.application.program.exception.DuplicatedProgramUrlException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.service.menu.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class MenuQueryService {
    private final MenuService menuService;

    @Autowired
    public MenuQueryService(
            MenuService menuService) {
        this.menuService = menuService;
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

        final List<MenuDto> allMenusDto = allMenus.stream().map(MenuDto::new).toList();
        MenuDtoFactory<MenuDto> menuDtoFactory = MenuDto::new;
        return rebuildHierarchyFromFlatMenuList(allMenusDto, menuDtoFactory);
    }

    public List<MenuDto> selectMenu() {
        final List<Menu> menus = menuService.selectMenu();
        return menus.stream().map(MenuDto::new).toList();
    }

    /**
     * 상위 객체의 subMenus를 비우고 컬렉션에 존재하는 자신의 하위 메뉴를 추가하여 재구성한다.
     * 평탄화된 MenuDto 컬렉션을 계층형으로 재 구성시 사용한다.
     *
     * @param targetMenus
     * @return
     */
    public <T extends MenuDto> List<T> rebuildHierarchyFromFlatMenuList(List<T> targetMenus, MenuDtoFactory<T> factory) {
        List<T> topMenus = new ArrayList<>();
        for (T menuDto : targetMenus) {
            menuDto.clearSubMenu();
            //상위메뉴를 갖고있지않고, topMenus에 없는 경우만 add
            if (!menuDto.hasUpperMenu()) {
                if (topMenus.stream().noneMatch(tm -> Objects.equals(tm.getId(), menuDto.getId()))) {
                    topMenus.add(menuDto);
                }
            } else {
                /**
                 * 전체 메뉴에서 상위메뉴를 찾고 상위메뉴가 topMenus에 이미 있다면 해당 메뉴의 하위메뉴로 add
                 * 없다면 상위메뉴의 하위메뉴로 add하고 상위메뉴를 topMenus에 add
                 */
                targetMenus.stream()
                        .filter(tm -> Objects.equals(tm.getId(), menuDto.getUpperMenu().getId()))
                        .forEach(topMenuDto -> {
                            final Optional<T> optionalTopMenu = topMenus.stream()
                                    .filter(tm -> Objects.equals(tm.getId(), topMenuDto.getId()))
                                    .findAny();

                            optionalTopMenu.ifPresentOrElse(
                                    tm -> tm.addSubMenu(menuDto),
                                    () ->
                                    {
                                        T newTopMenuDto = factory.create(topMenuDto); // 팩토리를 사용하여 객체 생성
                                        newTopMenuDto.addSubMenu(menuDto);
                                        topMenus.add(newTopMenuDto);
                                    }
                            );

                        });
            }
        }
        return topMenus;
    }


}
