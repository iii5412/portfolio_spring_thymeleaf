package com.portfolio.main.presentation.rest.account.login;

import com.portfolio.main.application.login.dto.JwtResponse;
import com.portfolio.main.application.login.dto.LoginRequest;
import com.portfolio.main.application.login.dto.UserRegist;
import com.portfolio.main.application.login.service.LoginService;
import com.portfolio.main.infrastructure.config.security.jwt.util.TokenUtil;
import com.portfolio.main.presentation.rest.response.SuccResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticationUser(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response,
            HttpServletRequest request) {
        final JwtResponse jwtResponse = loginService.login(loginRequest.getLoginId(), loginRequest.getLoginPw());
        TokenUtil.setTokenToResponse(response, jwtResponse);
        request.setAttribute("token", jwtResponse.getToken());
        return ResponseEntity.ok(new SuccResponse());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        loginService.logout(response);
        return ResponseEntity.ok(new SuccResponse());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegist userRegist) {
        userRegist.validate();
        loginService.signUp(userRegist);
        return ResponseEntity.ok(new SuccResponse());
    }
}
