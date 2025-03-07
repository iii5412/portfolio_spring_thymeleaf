package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.presentation.rest.TestAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuQueryServiceTest {

    @Autowired
    private TestAuth testAuth;

    @Autowired
    private MenuQueryService menuQueryService;

    @Test
    void findById() {
    }

    @Test
    void selectAllMenusByUserRole() {
        testAuth.setUserUserAndGetToken();
        final List<MenuDto> allMenu = menuQueryService.selectAllMenusByUserRole();
        assertEquals(2, allMenu.size());
        assertEquals(0, allMenu.getFirst().getSubMenus().size());
    }

    @Test
    void selectMenu() {
        testAuth.setUserAdminAndGetToken();
        final List<MenuDto> menuDtos = menuQueryService.selectMenu();
        assertTrue(menuDtos.size() > 0);
    }

    @Test
    void rebuildHierarchyFromFlatMenuList() {
    }
}
