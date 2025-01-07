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
     *
     * @return
     */
    public List<MenuDto> selectAllMenusByUserRole() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final RoleCode highestRoleCode = RoleCode.getHighestAuthority(authentication.getAuthorities());
        final List<Menu> allMenus = menuService.selectMenuByRoleCode(highestRoleCode);

        return allMenus.stream().map(MenuDto::new).toList();
    }

    public List<MenuDto> selectMenu() {
        final List<Menu> menus = menuService.selectMenu();
        return menus.stream().map(MenuDto::new).toList();
    }

    public List<MenuDto> selectMenuFlat() {
        return menuService.selectMenuFlat().stream().map(MenuDto::new).toList();
    }


}
