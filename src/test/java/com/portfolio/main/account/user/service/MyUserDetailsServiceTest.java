package com.portfolio.main.account.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyUserDetailsServiceTest {

    private String adminLoginId = "admin";

    @Autowired
    MyUserDetailsService userDetailsService;



    @Test
    void loadUserByUsername_admin() {
        userDetailsService.loadUserByUsername(adminLoginId);
    }
}
