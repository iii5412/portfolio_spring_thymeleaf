package com.portfolio.main.application.login.service;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.account.UserRole;
import com.portfolio.main.application.login.dto.JwtResponse;
import com.portfolio.main.application.login.dto.UserRegist;
import com.portfolio.main.application.login.exception.InvalidLoginId;
import com.portfolio.main.application.login.exception.InvalidLoginPassword;
import com.portfolio.main.domain.model.account.exception.RoleNotFoundException;
import com.portfolio.main.domain.repository.account.RoleRepository;
import com.portfolio.main.domain.repository.account.UserRepository;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.infrastructure.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.infrastructure.config.security.jwt.provider.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class LoginService {
    private final MyUserDetailsService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public JwtResponse login(String loginId, String loginPw) throws InvalidLoginId, InvalidLoginPassword {
        try {
            final User user = userService.validateUser(loginId, loginPw);
            final String jwt = jwtProvider.createToken(user);
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

    public Long signUp(UserRegist userRegist) {
        final User user = User.builder()
                .loginId(userRegist.getLoginId())
                .loginPw(passwordEncoder.encode(userRegist.getLoginPw1()))
                .username(userRegist.getUsername())
                .build();

        final Role role = roleRepository.findByRoleCode(RoleCode.ROLE_USER).orElseThrow(RoleNotFoundException::new);
        final UserRole userRole = new UserRole(user, role);

        user.addUserRole(userRole);
        final User savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}
