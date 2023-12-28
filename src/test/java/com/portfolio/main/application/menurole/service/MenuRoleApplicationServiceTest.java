package com.portfolio.main.application.menurole.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.menurole.dto.SaveMenuRole;
import com.portfolio.main.application.menurole.service.MenuRoleApplicationService;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.service.menu.MenuService;
import com.portfolio.main.domain.service.role.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuRoleApplicationServiceTest {

    @Autowired
    private MenuRoleApplicationService menuRoleApplicationService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Test
    void findTopRoleCodeByMenu() {
        //given
        final Menu menu = menuService.findById(4L);

        //when
        final RoleCode topRoleCodeByMenu = menuRoleApplicationService.findTopRoleCodeByMenu(menu.getId());

        //then
        assertEquals(topRoleCodeByMenu, RoleCode.ROLE_ADMIN);
    }

    @Test
    void createTopMenuRolesForMenus() {
        //given
        final PageRequest pageable = PageRequest.of(0, 10);
        final PageResult<Menu> menus = menuService.selectMenuWithPageable(SearchMenu.builder().build(), pageable);

        //when
        final List<MenuRole> topMenuRolesForMenus = menuRoleApplicationService.createTopMenuRolesForMenus(menus.getResult().stream().map(Menu::getId).toList());

        //then
        topMenuRolesForMenus.forEach(menuRole -> assertEquals(RoleCode.ROLE_ADMIN, menuRole.getRole().getRoleCode()));
    }

    @Test
    void changeRole() {
        final Menu menu = menuService.findById(4L);
        final RoleCode beforeTopRoleCodeByMenu = menuRoleApplicationService.findTopRoleCodeByMenu(menu.getId());
        assertEquals(RoleCode.ROLE_ADMIN, beforeTopRoleCodeByMenu);

        menuRoleApplicationService.changeRole(new MenuDto(menu), RoleCode.ROLE_ADMIN);

        final List<MenuRole> byMenu = menuRoleApplicationService.findByMenu(menu);
        assertEquals(1, byMenu.size());
        assertEquals(RoleCode.ROLE_ADMIN, byMenu.get(0).getRole().getRoleCode());
    }

    @Test
    void saveDuplicateData() {
        final Menu menu = menuService.findById(4L);
        final Role role = roleService.findById(2L);
        final List<MenuRoleDto> savedMenuRole = menuRoleApplicationService.save(new SaveMenuRole(menu.getId(), role.getId()));

        assertAll("검증",
                () -> assertEquals(2, savedMenuRole.size()),
                () -> assertTrue(
                        savedMenuRole.stream()
                                .anyMatch(mr -> mr.getRoleDto().getRoleCode().equals(RoleCode.ROLE_ADMIN))
                )
        );

    }

    @Test
    void save() {
        final Menu menu = menuService.findById(57L);
        final Role role = roleService.findById(2L);
        final List<MenuRoleDto> savedMenuRole = menuRoleApplicationService.save(new SaveMenuRole(menu.getId(), role.getId()));

        assertAll("검증",
                () -> assertEquals(2, savedMenuRole.size()),
                () -> assertTrue(
                        savedMenuRole.stream().anyMatch(mr -> mr.getRoleDto().getRoleCode().equals(RoleCode.ROLE_ADMIN))
                )
        );

    }

    @Test
    void deleteByMenuId() {
        final Menu menu = menuService.findById(4L);

        menuRoleApplicationService.deleteByMenuId(menu.getId());

        final List<MenuRole> byMenu = menuRoleApplicationService.findByMenu(menu);
        assertEquals(0, byMenu.size());
    }
}
