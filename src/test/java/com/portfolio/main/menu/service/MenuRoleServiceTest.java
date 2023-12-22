package com.portfolio.main.menu.service;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.role.exception.NotFoundRoleException;
import com.portfolio.main.account.role.service.RoleCode;
import com.portfolio.main.account.role.service.RoleService;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.MenuRole;
import com.portfolio.main.menu.dto.menu.MenuDto;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.util.page.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuRoleServiceTest {

    @Autowired
    private MenuRoleService menuRoleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Test
    void findTopRoleCodeByMenu() {
        //given
        final MenuDto menu = menuService.findById(4L);

        //when
        final RoleCode topRoleCodeByMenu = menuRoleService.findTopRoleCodeByMenu(menu);

        //then
        assertEquals(topRoleCodeByMenu, RoleCode.ROLE_ADMIN);
    }

    @Test
    void createTopMenuRolesForMenus() {
        //given
        final PageResult<MenuDto> menuPageResult = menuService.selectMenuWithPageable(SearchMenu.builder().build());
        final List<MenuDto> menus = menuPageResult.getResult();

        //when
        final List<MenuRole> topMenuRolesForMenus = menuRoleService.createTopMenuRolesForMenus(menus);

        //then
        topMenuRolesForMenus.forEach(menuRole -> assertEquals(RoleCode.ROLE_ADMIN, menuRole.getRole().getRoleCode()));
    }

    @Test
    void changeRole() {
        final MenuDto menu = menuService.findById(4L);
        final RoleCode beforeTopRoleCodeByMenu = menuRoleService.findTopRoleCodeByMenu(menu);
        assertEquals(RoleCode.ROLE_ADMIN, beforeTopRoleCodeByMenu);

        menuRoleService.changeRole(menu, RoleCode.ROLE_ADMIN);

        final List<MenuRole> byMenu = menuRoleService.findByMenu(menu);
        assertEquals(1, byMenu.size());
        assertEquals(RoleCode.ROLE_ADMIN, byMenu.get(0).getRole().getRoleCode());
    }

    @Test
    void saveDuplicateData() {
        final Menu menu = menuService.findById(4L);
        final Role role = roleService.findById(2L).orElseThrow(NotFoundRoleException::new);
        final List<MenuRole> savedMenuRole = menuRoleService.save(new MenuRole(new MenuRole.MenuRoleId(menu.getId(), role.getId()), menu, role));

        assertAll("검증",
                () -> assertEquals(2, savedMenuRole.size()),
                () -> assertTrue(
                        savedMenuRole.stream().anyMatch(mr -> mr.getRole().getRoleCode().equals(RoleCode.ROLE_ADMIN))
                )
        );

    }

    @Test
    void save() {
        final Menu menu = menuService.findById(57L);
        final Role role = roleService.findById(2L).orElseThrow(NotFoundRoleException::new);
        final List<MenuRole> savedMenuRole = menuRoleService.save(new MenuRole(new MenuRole.MenuRoleId(menu.getId(), role.getId()), menu, role));

        assertAll("검증",
                () -> assertEquals(2, savedMenuRole.size()),
                () -> assertTrue(
                        savedMenuRole.stream().anyMatch(mr -> mr.getRole().getRoleCode().equals(RoleCode.ROLE_ADMIN))
                )
        );

    }

    @Test
    void deleteByMenuId() {
        final Menu menu = menuService.findById(4L);

        menuRoleService.deleteByMenuId(menu.getId());

        final List<MenuRole> byMenu = menuRoleService.findByMenu(menu);
        assertEquals(0, byMenu.size());
    }
}
