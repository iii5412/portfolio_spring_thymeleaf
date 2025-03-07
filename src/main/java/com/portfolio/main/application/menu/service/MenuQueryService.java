package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.service.menu.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     */
    public List<MenuDto> selectAllMenusByUserRole() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final RoleCode highestRoleCode = RoleCode.getHighestAuthority(authentication.getAuthorities());
        final List<Menu> menusByRoleCode = menuService.selectMenuByRoleCode(highestRoleCode);
        final List<MenuDto> menuDtos = menusByRoleCode.stream()
                .map(MenuDto::new)
                .toList();

        return rebuildSubMenus(menuDtos);
    }


    public List<MenuDto> selectMenu() {
        final List<Menu> menus = menuService.selectMenu();
        return menus.stream().map(MenuDto::new).toList();
    }

    /**
     * <pre>
     *     메뉴를 평면적으로 조회한다.
     * </pre>
     *
     * @return List<MenuDto>
     */
    public List<MenuDto> selectMenuFlat() {
        return menuService.selectMenuFlat().stream().map(MenuDto::new).toList();
    }

    /**
     * <pre>
     *     하위 메뉴를 재구성하여 계층형 구조로 만든다.
     *     1. menuDtos를 순회하며 상위 메뉴가 있는지 확인한다.
     *     2. 상위 메뉴가 있다면, menuDtos에서 상위 메뉴를 찾는다.
     *     3. 상위 메뉴가 존재한다면, 해당 상위 메뉴에 하위 메뉴를 추가한다.
     *     4. 하위 메뉴가 존재하는 상위메뉴만 반환한다.
     * </pre>
     *
     * @param menuDtos 메뉴 목록
     * @return 계층형 구조로 재구성된 메뉴 목록
     */
    private List<MenuDto> rebuildSubMenus(List<MenuDto> menuDtos) {
        for (MenuDto menuDto : menuDtos) {
            menuDto.setSubMenus(List.of());
            if (menuDto.hasUpperMenu()) {
                final Long upperMenuId = menuDto.getUpperMenuId();
                menuDtos.stream()
                        .filter(m -> m.getId().equals(upperMenuId))
                        .findFirst()
                        .ifPresent(parentMenu -> parentMenu.addSubMenu(menuDto));
            }
        }

        return menuDtos.stream()
                .filter(menuDto -> !menuDto.hasUpperMenu())
                .filter(MenuDto::hasSubMenus)
                .toList();
    }
}
