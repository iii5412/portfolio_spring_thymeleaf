package com.portfolio.main.presentation.rest.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.main.application.menu.dto.CreateMenu;
import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.service.MenuApplicationService;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.exception.MenuNotFoundException;
import com.portfolio.main.domain.model.menu.type.MenuType;
import com.portfolio.main.infrastructure.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.presentation.rest.TestAuth;
import com.portfolio.main.presentation.rest.menu.request.EditMenuRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuApplicationService menuApplicationService;

    @Autowired
    private TestAuth testAuth;
    private String requestMapijng = "/menu";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void when_select_all_menus() throws Exception {
        mockMvc.perform(
                        get(requestMapijng)
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void when_selectMenu_not_admin() throws Exception {
        final String token = testAuth.setUserAdminAndGetToken();
        mockMvc.perform(
                        get(requestMapijng + "/flat")
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                ).andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void when_find_invalid_menu_id() throws Exception {
        final String token = testAuth.setUserAdminAndGetToken();
        mockMvc.perform(
                        get(requestMapijng + "/123")
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                ).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void when_selectMenu() throws Exception {
        final String token = testAuth.setUserAdminAndGetToken();
        mockMvc.perform(
                        get(requestMapijng + "/flat")
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                                .characterEncoding(StandardCharsets.UTF_8)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void save() throws Exception {
        final String token = testAuth.setUserAdminAndGetToken();
        final CreateMenu createMenu = new CreateMenu("테스트 부모", MenuType.FOLDER, 2L, RoleCode.ROLE_ADMIN, "admin");
        final String createMenuJson = objectMapper.writeValueAsString(createMenu);

        mockMvc.perform(
                        post(requestMapijng + "/")
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(createMenuJson)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteMenu() throws Exception {
        final String token = testAuth.setUserAdminAndGetToken();
        final CreateMenu createMenu = new CreateMenu("테스트 부모", MenuType.FOLDER, 2L, RoleCode.ROLE_ADMIN, "admin");
        final Long savedId = menuApplicationService.saveMenu(createMenu);

        mockMvc.perform(
                        delete(requestMapijng + "/" + savedId)
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                                .characterEncoding(StandardCharsets.UTF_8)
                ).andExpect(status().isOk())
                .andDo(print());

        assertThrows(MenuNotFoundException.class, () -> menuApplicationService.findById(savedId));
    }

    @Test
    void editMenu() throws Exception {
        final String token = testAuth.setUserAdminAndGetToken();
        final Long testMenuId = 2L;
        final MenuDto testTargetMenu = menuApplicationService.findById(testMenuId);
        final EditMenuRequest editMenu = new EditMenuRequest(testTargetMenu.getId(), testTargetMenu.getUpperMenuId(), "테스트", MenuType.FOLDER, 99L, null, RoleCode.ROLE_ADMIN);
        final String editMenuString = objectMapper.writeValueAsString(editMenu);

        mockMvc.perform(
                        patch(requestMapijng + "/")
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(editMenuString)
                ).andExpect(status().isOk())
                .andDo(print());

        final MenuDto editedMenu = menuApplicationService.findById(testMenuId);
        assertEquals("테스트", editedMenu.getMenuName());
        assertEquals(MenuType.FOLDER, editedMenu.getMenuType());
        assertEquals(99L, editedMenu.getOrderNum());
    }
}
