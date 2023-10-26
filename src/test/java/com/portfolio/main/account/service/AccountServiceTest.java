package com.portfolio.main.account.service;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.dto.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AccountServiceTest {

    private User adminUser;
    private User user;

    @Autowired
    AccountService accountService;

    @BeforeEach
    void setup() {
        this.adminUser = User.builder()
                .loginId("test")
                .loginPw("1234")
                .username("관리자")
                .build();
    }

    @Test
    void login() {
        final JwtResponse jwtResponse = accountService.login(adminUser.getLoginId(), adminUser.getLoginPw());
        final String token = jwtResponse.getToken();

        log.info("token => {}", token);

        assertTrue(StringUtils.isEmpty(token));
    }

    @Test
    void signUp() {
    }
}
