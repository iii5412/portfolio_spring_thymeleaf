package com.portfolio.main.controller.api.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.main.account.user.service.MyUserDetailsService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private MyUserDetailsService userDetailsService;

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
        setAuthenticationUser();
        mockMvc.perform(
                        get(requestMapijng + "/manage?page=1&size=20")
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void when_selectMenu() throws Exception {
        setAuthenticationAdmin();
        mockMvc.perform(
                        get(requestMapijng + "/manage?page=1&size=20")
                                .contentType(APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void save() throws Exception {
        setAuthenticationAdmin();
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
    void deleteMenu() throws Exception {
        setAuthenticationAdmin();
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
    void editMenu() throws Exception {
        setAuthenticationAdmin();
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


    private void setAuthenticationAdmin() {
        setUserAuthentication("admin");
    }

    private void setAuthenticationUser() {
        setUserAuthentication("user");
    }

    private void setUserAuthentication(String type) {
        String loginId = "admin";

        if(!type.equals("admin"))
            loginId = "testUser";

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
        final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
