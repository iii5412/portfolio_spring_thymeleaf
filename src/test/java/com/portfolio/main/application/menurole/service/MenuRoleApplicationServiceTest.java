package com.portfolio.main.application.menurole.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.application.menu.service.MenuQueryService;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.menurole.dto.SaveMenuRole;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.service.account.role.RoleService;
import com.portfolio.main.presentation.rest.TestAuth;
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
    private MenuQueryService menuQueryService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TestAuth testAuth;

    @Test
    void findTopRoleCodeByMenu() {
        //given
        final MenuDto menu = menuQueryService.findById(1L);

        //when
        final RoleCode topRoleCodeByMenu = menuRoleApplicationService.findTopRoleCodeByMenu(menu.getId());

        //then
        assertEquals(topRoleCodeByMenu, RoleCode.ROLE_ADMIN);
    }

    @Test
    void changeRole() {
        final List<MenuDto> menuDtos = menuQueryService.selectMenu();
        final MenuDto menuDto = menuDtos.get(0);
        final RoleCode beforeTopRoleCodeByMenu = menuRoleApplicationService.findTopRoleCodeByMenu(menuDto.getId());
        assertEquals(RoleCode.ROLE_ADMIN, beforeTopRoleCodeByMenu);

        menuRoleApplicationService.changeRole(menuDto, RoleCode.ROLE_USER);

        final List<MenuRoleDto> byMenu = menuRoleApplicationService.findByMenu(menuDto);
        assertEquals(2, byMenu.size());
        assertEquals(RoleCode.ROLE_ADMIN, byMenu.get(0).getRoleDto().getRoleCode());
    }

    @Test
    void saveDuplicateData() {
        final List<MenuDto> menuDtosList = menuQueryService.selectMenu();
        final MenuDto targetMenuDto = menuDtosList.get(0);
        final List<MenuRoleDto> menuRoleDtoList = menuRoleApplicationService.findByMenu(targetMenuDto);
        final MenuRoleDto targetMenuRoleDto = menuRoleDtoList.get(0);
        final Long menuId = targetMenuRoleDto.getMenuDto().getId();
        final Long roleId = targetMenuRoleDto.getRoleDto().getId();
        final List<MenuRoleDto> savedMenuRole = menuRoleApplicationService.save(new SaveMenuRole(menuId, roleId));

        assertAll("검증",
                () -> assertTrue(
                        savedMenuRole.stream()
                                .anyMatch(mr -> mr.getRoleDto().getRoleCode().equals(RoleCode.ROLE_ADMIN))
                )
        );

    }

    @Test
    void save() {
        testAuth.setUserAdminAndGetToken();
        final MenuDto menuDto = menuQueryService.selectAllMenusByUserRole().get(0);
        final Role role = roleService.findById(2L);
        final List<MenuRoleDto> savedMenuRole = menuRoleApplicationService.save(new SaveMenuRole(menuDto.getId(), role.getId()));

        assertAll("검증",
                () -> assertTrue(
                        savedMenuRole.stream().anyMatch(mr -> mr.getRoleDto().getRoleCode().equals(RoleCode.ROLE_ADMIN))
                )
        );

    }

    @Test
    void deleteByMenuId() {
        final MenuDto menuDto = menuQueryService.selectMenu().get(0);

        menuRoleApplicationService.deleteByMenuId(menuDto.getId());

        final List<MenuRoleDto> byMenu = menuRoleApplicationService.findByMenu(menuDto);
        assertEquals(0, byMenu.size());
    }
}
