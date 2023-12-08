package com.portfolio.main.menu.service;

import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.dto.menu.CreateMenu;
import com.portfolio.main.menu.dto.menu.EditMenu;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.menu.dto.program.CreateProgram;
import com.portfolio.main.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.menu.exception.MenuNotFoundException;
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
class MenuServiceTest {
    private String testModifiedBy = "test";
    @Autowired
    private MenuService menuService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private EntityManager entityManager;

    @Test
    void findAll() {
        final List<Menu> allMenu = menuService.findAllWithProgram();
        assertEquals(1, allMenu.size());
        assertEquals(3, allMenu.get(0).getSubMenus().size());
    }

    @Test
    void selectMenu() {
        final SearchMenu searchMenu = SearchMenu.builder().menuName("메뉴").build();
        final List<Menu> menus = menuService.selectMenu(searchMenu);
        assertEquals(1, menus.size());
        assertEquals(1, menus.get(0).getSubMenus().size());
    }

    @Test
    void saveTopFolderMenu() {
        //given
        final CreateMenu createMenu = new CreateMenu("폴더메뉴", MenuType.FOLDER, 1L, testModifiedBy);

        //when
        final Long savedMenuId = menuService.saveMenu(createMenu);
        final Menu savedMenu = menuService.findById(savedMenuId);

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
        final List<Menu> menus = menuService.selectMenu(SearchMenu.builder().menuName(paretnMenuName).build());
        final Menu parentMenu = menus.get(0);
        assertEquals(parentMenu.getId(), 1L);
        final CreateMenu createMenu = new CreateMenu(parentMenu.getId(), newChildMenuName, MenuType.PROGRAM, 3L, testModifiedBy);

        //when
        menuService.saveMenu(createMenu);

        //then
        final Menu findParentMenu = menuService.selectMenu(SearchMenu.builder().menuName(paretnMenuName).build()).get(0);
        final Optional<Menu> findSavedMenu = findParentMenu.getSubMenus().stream().filter(menu -> menu.getMenuName().equals(newChildMenuName)).findFirst();


        assertTrue(findSavedMenu.isPresent());
    }

    @Test
    void whenDeleteMenuWithSubmenus_thenExceptionIsThrown() {
        String targetMenuName = "환경설정";
        final List<Menu> menus = menuService.selectMenu(SearchMenu.builder().menuName(targetMenuName).build());
        final Menu targetMenu = menus.get(0);
        assertEquals(targetMenu.getId(), 1L);

        assertThrows(CannotDeleteMenuWithSubmenusException.class, () -> {
            menuService.deleteMenu(targetMenu.getId());
        });
    }

    @Test
    void deleteMenu() {
        //given
        final Long saved_2_lvl_menuId = menuService.saveMenu(new CreateMenu(1L, "테스트2레벨", MenuType.FOLDER, 3L, testModifiedBy));
        final Long saved_3_lvl_menuId = menuService.saveMenu(new CreateMenu(saved_2_lvl_menuId, "테스트3레벨", MenuType.PROGRAM, 0L, testModifiedBy));

        //when
        menuService.deleteMenu(saved_3_lvl_menuId);

        //then
        assertThrows(MenuNotFoundException.class, () -> menuService.findById(saved_3_lvl_menuId));
    }

    @Test
    void editMenu() {
        //given
        final Long savedTestParentMenuId = menuService.saveMenu(new CreateMenu("테스트", MenuType.FOLDER, 2L, "admin"));
        final List<Menu> menus = menuService.selectMenu(SearchMenu.builder().id(2L).build());
        final Long savedTestProgramId = programService.create(new CreateProgram("테스트_프로그램", "/testProgram", "admin"));
        final Menu targetMenu = menus.get(0);
        final EditMenu editMenu = new EditMenu(savedTestParentMenuId, "사용자관리2", MenuType.PROGRAM, 99L, savedTestProgramId);

        //when
        menuService.edit(targetMenu.getId(), editMenu);

        entityManager.flush();
        entityManager.clear();

        //then
        final Menu editedMenu = menuService.findById(targetMenu.getId());
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
