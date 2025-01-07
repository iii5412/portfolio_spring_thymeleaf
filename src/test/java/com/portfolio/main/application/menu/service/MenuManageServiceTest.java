package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.menu.dto.*;
import com.portfolio.main.application.program.service.ProgramManageService;
import com.portfolio.main.application.program.service.ProgramQueryService;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.presentation.rest.TestAuth;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.domain.model.menu.exception.MenuNotFoundException;
import com.portfolio.main.domain.model.menu.type.MenuType;
import com.portfolio.main.common.util.page.PageResult;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuManageServiceTest {
    private final String testModifiedBy = TestAuth.USER_ADMIN;
    @Autowired
    private MenuQueryService menuQueryService;
    @Autowired
    private MenuManageService menuManageService;
    @Autowired
    private ProgramQueryService programQueryService;
    @Autowired
    private ProgramManageService programManageService;
    @Autowired
    private TestAuth testAuth;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        testAuth.setUserAdminAndGetToken();
    }

    @Test
    void selectMenu() {
        final SearchMenu searchMenu = SearchMenu.builder().build();
        final List<MenuManageDto> menuDtos = menuManageService.selectMenu(searchMenu);
        assertEquals(1, menuDtos.size());
    }

    @Test
    void saveTopFolderMenu() {
        //given
        final CreateMenu createMenu = new CreateMenu("폴더메뉴", MenuType.FOLDER, 1L, RoleCode.ROLE_ADMIN, testModifiedBy);

        //when
        final Long savedMenuId = menuManageService.createMenu(createMenu);
        final MenuDto savedMenu = menuQueryService.findById(savedMenuId);

        //then
        assertEquals("폴더메뉴", savedMenu.getMenuName());
        assertEquals(MenuType.FOLDER, savedMenu.getMenuType());
        assertEquals(testModifiedBy, savedMenu.getLastModifiedByUser().getLoginId());
    }

    @Test
    void saveChildProgramMenu() {
        String paretnMenuName = "환경설정";
        String newChildMenuName = "테스트메뉴";

        //given
        final List<MenuManageDto> menus = menuManageService.selectMenu(SearchMenu.builder().menuName(paretnMenuName).build());
        final MenuManageDto parentMenu = menus.get(0);
        assertEquals(parentMenu.getId(), 1L);
        final CreateMenu createMenu = new CreateMenu(parentMenu.getId(), newChildMenuName, MenuType.PROGRAM,  3L, RoleCode.ROLE_ADMIN, testModifiedBy);

        //when
        menuManageService.createMenu(createMenu);

        //then
        final List<MenuManageDto> afterMenus = menuManageService.selectMenu(SearchMenu.builder().menuName(paretnMenuName).build());
        final MenuManageDto findParentMenu = afterMenus.get(0);
        final Optional<MenuManageDto> findSavedMenu = findParentMenu.getSubMenus().stream().filter(menu -> menu.getMenuName().equals(newChildMenuName)).findFirst();

        assertTrue(findSavedMenu.isPresent());
    }

    @Test
    void whenDeleteMenuWithSubmenus_thenExceptionIsThrown() {
        String targetMenuName = "환경설정";
        final List<MenuManageDto> menus = menuManageService.selectMenu(SearchMenu.builder().menuName(targetMenuName).build());
        final MenuManageDto targetMenu = menus.get(0);
        assertEquals(targetMenu.getId(), 1L);

        assertThrows(CannotDeleteMenuWithSubmenusException.class, () -> menuManageService.deleteMenu(targetMenu.getId()));
    }

    @Test
    void deleteMenu() {
        //given
        final Long saved_2_lvl_menuId = menuManageService.createMenu(new CreateMenu(1L, "테스트2레벨", MenuType.FOLDER, 3L, RoleCode.ROLE_ADMIN, testModifiedBy));
        final Long saved_3_lvl_menuId = menuManageService.createMenu(new CreateMenu(saved_2_lvl_menuId, "테스트3레벨", MenuType.PROGRAM, 0L, RoleCode.ROLE_ADMIN, testModifiedBy));

        //when
        menuManageService.deleteMenu(saved_3_lvl_menuId);

        //then
        assertThrows(MenuNotFoundException.class, () -> menuQueryService.findById(saved_3_lvl_menuId));
    }

    @Test
    void editMenu() {
        //given
        final Long savedTestParentMenuId = menuManageService.createMenu(new CreateMenu("테스트", MenuType.FOLDER, 2L, RoleCode.ROLE_ADMIN, testModifiedBy));
        final Long targetMenuId = menuManageService.createMenu(new CreateMenu("테스트2", MenuType.PROGRAM, 0L, RoleCode.ROLE_ADMIN, testModifiedBy));

        final MenuDto targetMenu = menuQueryService.findById(targetMenuId);
        final Long savedTestProgramId = programManageService.create(new CreateProgram("테스트_프로그램", "/testProgram", testModifiedBy));
        final EditMenu editMenu = new EditMenu(targetMenu.getId(), savedTestParentMenuId, "사용자관리2", MenuType.PROGRAM, 99L, savedTestProgramId, RoleCode.ROLE_ADMIN, testModifiedBy);

        //when
        menuManageService.editMenu(editMenu);

        entityManager.flush();
        entityManager.clear();

        //then
        final MenuDto editedMenu = menuQueryService.findById(targetMenu.getId());
        assertEquals(savedTestParentMenuId, editMenu.getUpperId());
        assertEquals("테스트_프로그램", editedMenu.getProgram().getProgramName());
        assertEquals(savedTestProgramId, editedMenu.getProgram().getId());
        assertEquals("사용자관리2", editedMenu.getMenuName());
        assertEquals(editedMenu.getLastModifiedByUser().getLoginId(), testModifiedBy);

    }
}
