package com.portfolio.main.application.login.service;

import com.portfolio.main.application.login.dto.UserCreateDto;
import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.presentation.rest.account.login.response.JwtResponse;
import com.portfolio.main.presentation.rest.account.login.request.SignUpRequest;
import com.portfolio.main.application.login.exception.InvalidLoginId;
import com.portfolio.main.application.login.exception.InvalidLoginPassword;
import com.portfolio.main.application.login.exception.InvalidRegistUser;
import com.portfolio.main.domain.model.account.type.RoleCode;
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
    LoginService loginService;

    @Autowired
    UserQueryService userQueryService;

    @Test
    void login_invalid_loginId() {
        String invalidLoginId = "invalidLoginId";
        assertThrows(InvalidLoginId.class, () -> {
            final JwtResponse jwtResponse = loginService.login(invalidLoginId, null);
        });
    }

    @Test
    @Transactional
    void login() {
        //given
        final UserCreateDto userCreateDto = createTestUserRegist();
        final Long userId = loginService.signUp(userCreateDto);
        final UserDto findUser = userQueryService.findById(userId);

        //when
        final JwtResponse jwtResponse = loginService.login(findUser.getLoginId(), userCreateDto.getLoginPw());

        log.info("jwtResponse.getToken() => {}", jwtResponse.getToken());

        //then
        assertTrue(StringUtils.hasText(jwtResponse.getToken()));
    }


    @Test
    void signUp_UserRegist_validate() {
        final SignUpRequest signUpRequest = new SignUpRequest();
        assertThrows(InvalidRegistUser.class, signUpRequest::validate);
    }

    @Test
    @Transactional
    void signUp() {
        //given
        final UserCreateDto userCreateDto = createTestUserRegist();

        //when
        loginService.signUp(userCreateDto);

        //then
        final UserDto findUser = userQueryService.findByLoginId(userCreateDto.getLoginId());
        final boolean hasUserRole = findUser.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRoleCode().equals(RoleCode.ROLE_USER));

        assertEquals(findUser.getLoginId(), userCreateDto.getLoginId());
        assertTrue(hasUserRole);
    }

    private UserCreateDto createTestUserRegist() {
        final SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("테스트")
                .loginId("test")
                .loginPw1("1234")
                .loginPw2("1234")
                .build();
        return new UserCreateDto(signUpRequest);
    }
}
