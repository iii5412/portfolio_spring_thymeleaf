package com.portfolio.main.presentation.rest.role;

import com.portfolio.main.infrastructure.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
import com.portfolio.main.presentation.rest.TestAuth;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private TestAuth testAuth;
    private final String requestMapping = "/role";

    private String token = "";

    @BeforeEach
    void setup(){
        token = testAuth.setUserAdminAndGetToken();

    }

    @Test
    void findAllRoles() throws Exception {
        mockMvc.perform(get(requestMapping)
                        .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].childRoles").isArray())
                .andDo(print());
    }

}
