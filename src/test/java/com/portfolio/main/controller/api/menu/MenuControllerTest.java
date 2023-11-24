package com.portfolio.main.controller.api.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.dto.menu.CreateMenu;
import com.portfolio.main.menu.dto.menu.EditMenu;
import com.portfolio.main.menu.exception.MenuNotFoundException;
import com.portfolio.main.menu.service.MenuService;
import com.portfolio.main.menu.service.MenuType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuService menuService;
    private String requestMapijng = "/menu";
    private String token = "";
    private final ObjectMapper objectMapper = new ObjectMapper();



    @Test
    void when_select_all_menus() throws Exception {
        mockMvc.perform(
                        get(requestMapijng + "/")
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void when_selectMenu_not_admin() throws Exception {
        mockMvc.perform(
                        get(requestMapijng + "/manage?page=1&size=20")
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/loginPage"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void when_selectMenu() throws Exception {
        mockMvc.perform(
                        get(requestMapijng + "/manage?page=1&size=20")
                                .contentType(APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void save() throws Exception {
        final CreateMenu createMenu = new CreateMenu("테스트 부모", MenuType.FOLDER, 2L, "admin");
        final String createMenuJson = objectMapper.writeValueAsString(createMenu);

        mockMvc.perform(
                        post(requestMapijng + "/")
                                .contentType(APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(createMenuJson)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteMenu() throws Exception {
        final CreateMenu createMenu = new CreateMenu("테스트 부모", MenuType.FOLDER, 2L, "admin");
        final Long savedId = menuService.saveMenu(createMenu);

        mockMvc.perform(
                        delete(requestMapijng + "/" + savedId)
                                .contentType(APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                ).andExpect(status().isOk())
                .andDo(print());

        assertThrows(MenuNotFoundException.class, () -> menuService.findById(savedId));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void editMenu() throws Exception {
        final Long testMenuId = 2L;
        final Menu testTargetMenu = menuService.findById(testMenuId);
        final EditMenu editMenu = new EditMenu(testTargetMenu.getUpperMenu().getId(), "테스트", MenuType.FOLDER, 99L, null);
        final String editMenuString = objectMapper.writeValueAsString(editMenu);

        mockMvc.perform(
                        patch(requestMapijng + "/" + testTargetMenu.getId())
                                .contentType(APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(editMenuString)
                ).andExpect(status().isOk())
                .andDo(print());

        final Menu editedMenu = menuService.findById(testMenuId);
        assertEquals("테스트", editedMenu.getMenuName());
        assertEquals(MenuType.FOLDER, editedMenu.getMenuType());
        assertEquals(99L, editedMenu.getOrderNum());
    }



}
