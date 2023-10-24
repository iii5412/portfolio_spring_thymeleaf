package com.learn.learnJwt.auth;

import com.learn.learnJwt.security.jwt.JwtAuthenticationToken;
import com.learn.learnJwt.security.jwt.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class LoginController {
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticationUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            final JwtResponse jwtResponse = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            TokenUtil.setTokenToResponse(response, jwtResponse);
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) throws IOException {
        final Cookie jwtCookie = new Cookie(JwtAuthenticationToken.TOKEN_NAME, null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }
}
