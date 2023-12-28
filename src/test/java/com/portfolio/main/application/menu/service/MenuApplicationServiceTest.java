package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.program.service.ProgramApplicationService;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.presentation.rest.TestAuth;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.application.menu.dto.CreateMenu;
import com.portfolio.main.application.menu.dto.EditMenu;
import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.domain.model.menu.exception.MenuNotFoundException;
import com.portfolio.main.domain.model.menu.type.MenuType;
import com.portfolio.main.common.util.page.PageResult;
import jakarta.persistence.EntityManager;
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
class MenuApplicationServiceTest {
    private String testModifiedBy = "testUser";
    @Autowired
    private MenuApplicationService menuApplicationService;
    @Autowired
    private ProgramApplicationService programApplicationService;
    @Autowired
    private TestAuth testAuth;
    @Autowired
    private EntityManager entityManager;

    @Test
    void findAll() {
        testAuth.setUserGuest();
        final List<MenuDto> allMenu = menuApplicationService.selectAllMenusByUserRole();
        assertEquals(1, allMenu.size());
        assertEquals(1, allMenu.get(0).getSubMenus().size());
    }

    @Test
    void selectMenu() {
        final SearchMenu searchMenu = SearchMenu.builder().id(2L).build();
        final List<MenuDto> menuDtos = menuApplicationService.selectMenu(searchMenu);
        assertEquals(2, menuDtos.size());
    }

    @Test
    void saveTopFolderMenu() {
        //given
        final CreateMenu createMenu = new CreateMenu("폴더메뉴", MenuType.FOLDER, 1L, RoleCode.ROLE_ADMIN, testModifiedBy);

        //when
        final Long savedMenuId = menuApplicationService.saveMenu(createMenu);
        final MenuDto savedMenu = menuApplicationService.findById(savedMenuId);

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
        final PageResult<MenuDto> menus = menuApplicationService.selectMenuWithPageable(SearchMenu.builder().menuName(paretnMenuName).build());
        final MenuDto parentMenu = menus.getResult().get(0);
        assertEquals(parentMenu.getId(), 1L);
        final CreateMenu createMenu = new CreateMenu(parentMenu.getId(), newChildMenuName, MenuType.PROGRAM,  3L, RoleCode.ROLE_ADMIN, testModifiedBy);

        //when
        menuApplicationService.saveMenu(createMenu);

        //then
        final PageResult<MenuDto> menuPageResult = menuApplicationService.selectMenuWithPageable(SearchMenu.builder().menuName(paretnMenuName).build());
        final MenuDto findParentMenu = menuPageResult.getResult().get(0);
        final Optional<MenuDto> findSavedMenu = findParentMenu.getSubMenus().stream().filter(menu -> menu.getMenuName().equals(newChildMenuName)).findFirst();

        assertTrue(findSavedMenu.isPresent());
    }

    @Test
    void whenDeleteMenuWithSubmenus_thenExceptionIsThrown() {
        String targetMenuName = "환경설정";
        final PageResult<MenuDto> menus = menuApplicationService.selectMenuWithPageable(SearchMenu.builder().menuName(targetMenuName).build());
        final MenuDto targetMenu = menus.getResult().get(0);
        assertEquals(targetMenu.getId(), 1L);

        assertThrows(CannotDeleteMenuWithSubmenusException.class, () -> menuApplicationService.deleteMenu(targetMenu.getId()));
    }

    @Test
    void deleteMenu() {
        //given
        final Long saved_2_lvl_menuId = menuApplicationService.saveMenu(new CreateMenu(1L, "테스트2레벨", MenuType.FOLDER, 3L, RoleCode.ROLE_ADMIN, testModifiedBy));
        final Long saved_3_lvl_menuId = menuApplicationService.saveMenu(new CreateMenu(saved_2_lvl_menuId, "테스트3레벨", MenuType.PROGRAM, 0L, RoleCode.ROLE_ADMIN, testModifiedBy));

        //when
        menuApplicationService.deleteMenu(saved_3_lvl_menuId);

        //then
        assertThrows(MenuNotFoundException.class, () -> menuApplicationService.findById(saved_3_lvl_menuId));
    }

    @Test
    void editMenu() {
        //given
        final Long savedTestParentMenuId = menuApplicationService.saveMenu(new CreateMenu("테스트", MenuType.FOLDER, 2L, RoleCode.ROLE_ADMIN, "admin"));
        final List<MenuDto> menuDtos = menuApplicationService.selectMenu(SearchMenu.builder().id(2L).build());
        final Long savedTestProgramId = programApplicationService.create(new CreateProgram("테스트_프로그램", "/testProgram", "admin"));
        final MenuDto targetMenu = menuDtos.get(0);
        final EditMenu editMenu = new EditMenu(targetMenu.getId(), savedTestParentMenuId, "사용자관리2", MenuType.PROGRAM, 99L, savedTestProgramId, RoleCode.ROLE_ADMIN, "admin");

        //when
        menuApplicationService.edit(editMenu);

        entityManager.flush();
        entityManager.clear();

        //then
        final MenuDto editedMenu = menuApplicationService.findById(targetMenu.getId());
        assertEquals(savedTestParentMenuId, editMenu.getUpperId());
        assertEquals("테스트_프로그램", editedMenu.getProgram().getProgramName());
        assertEquals(savedTestProgramId, editedMenu.getProgram().getId());
        assertEquals("사용자관리2", editedMenu.getMenuName());
        assertEquals(editedMenu.getLastModifiedByUser().getLoginId(), "admin");

    }

    private List<Menu> getLastChildSubMunus(Menu menu) {
        List<Menu> resultMenus = new ArrayList<>();
        if (menu.hasSubMenus()) {
            boolean isLastLevel = true;
            for (Menu subMenu : menu.getSubMenus()) {
                if (subMenu.hasSubMenus()) {
                    isLastLevel = false;
                    resultMenus = getLastChildSubMunus(subMenu);
                }
            }

            if (isLastLevel)
                resultMenus = menu.getSubMenus();
        }
        return resultMenus;
    }
}
