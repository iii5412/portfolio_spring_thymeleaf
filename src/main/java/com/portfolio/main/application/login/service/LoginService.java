package com.portfolio.main.application.login.service;

import com.portfolio.main.application.login.dto.UserCreateDto;
import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.login.exception.InvalidLoginId;
import com.portfolio.main.application.login.exception.InvalidLoginPassword;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.service.RoleApplicationService;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.service.account.UserService;
import com.portfolio.main.infrastructure.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.infrastructure.config.security.jwt.provider.JwtProvider;
import com.portfolio.main.presentation.rest.account.login.response.JwtResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class LoginService {
    private final UserService userService;
    private final UserQueryService userQueryService;
    private final RoleApplicationService roleApplicationService;
    private final JwtProvider jwtProvider;

    public JwtResponse login(String loginId, String loginPw) throws InvalidLoginId, InvalidLoginPassword {
        try {
            userService.validateLoginCredentials(loginId, loginPw);

            final UserDto userDto = userQueryService.findByLoginId(loginId);

            final String jwt = jwtProvider.createToken(userDto);
            return new JwtResponse(jwt);
        } catch (InvalidLoginId | InvalidLoginPassword e) {
            throw e;
        }
    }

    public void logout(HttpServletResponse response) {
        final Cookie jwtCookie = new Cookie(JwtAuthenticationToken.TOKEN_NAME, null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
    }

    public Long signUp(UserCreateDto userCreateDto) {
        final RoleDto roleDto = roleApplicationService.findByRoleCode(RoleCode.ROLE_USER);

        return userService.signUp(userCreateDto, roleDto);
    }


}
