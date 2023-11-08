package com.portfolio.main.account.service;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.login.dto.JwtResponse;
import com.portfolio.main.account.login.dto.UserRegist;
import com.portfolio.main.account.login.exception.InvalidLoginId;
import com.portfolio.main.account.login.exception.InvalidLoginPassword;
import com.portfolio.main.account.login.exception.InvalidRegistUser;
import com.portfolio.main.account.login.service.LoginService;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.account.user.service.RoleCode;
import com.portfolio.main.account.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class LoginServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Test
    void login_invalid_loginId() {
        String invalidLoginId = "invalidLoginId";
        assertThrows(InvalidLoginId.class, () -> {
            final JwtResponse jwtResponse = loginService.login(invalidLoginId, null);
        });
    }

    @Test
    @Transactional
    void login_invalid_password() {
        //given
        final UserRegist testUserRegist = createTestUserRegist();
        final Long userId = loginService.signUp(testUserRegist);
        final User findUser = userDetailsService.findByUserId(userId);

        log.info("findUser.getLoginPw() = {}", findUser.getLoginPw());

        //expected
        assertThrows(InvalidLoginPassword.class, () -> {
            loginService.login(findUser.getLoginId(), findUser.getLoginPw() + "!");
        });
    }

    @Test
    @Transactional
    void login() {
        //given
        final UserRegist testUserRegist = createTestUserRegist();
        final Long userId = loginService.signUp(testUserRegist);
        final User findUser = userDetailsService.findByUserId(userId);

        //when
        final JwtResponse jwtResponse = loginService.login(findUser.getLoginId(), testUserRegist.getLoginPw1());

        log.info("jwtResponse.getToken() => {}", jwtResponse.getToken());

        //then
        assertTrue(StringUtils.hasText(jwtResponse.getToken()));
    }


    @Test
    void signUp_UserRegist_validate() {
        final UserRegist userRegist = new UserRegist();
        assertThrows(InvalidRegistUser.class, userRegist::validate);
    }

    @Test
    @Transactional
    void signUp() {
        //given
        final UserRegist userRegist = createTestUserRegist();

        //when
        loginService.signUp(userRegist);

        //then
        final User findUser = userDetailsService.findUserByLoginId(userRegist.getLoginId());
        final boolean hasUserRole = findUser.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getRoleCode().equals(RoleCode.ROLE_USER));

        assertEquals(findUser.getLoginId(), userRegist.getLoginId());
        assertTrue(hasUserRole);
    }

    private UserRegist createTestUserRegist() {
        return UserRegist.builder()
                .username("테스트")
                .loginId("test")
                .loginPw1("1234")
                .loginPw2("1234")
                .build();
    }
}
