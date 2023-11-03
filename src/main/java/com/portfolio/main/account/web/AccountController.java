package com.portfolio.main.account.web;

import com.portfolio.main.account.login.dto.JwtResponse;
import com.portfolio.main.account.login.dto.LoginRequest;
import com.portfolio.main.account.login.dto.UserRegist;
import com.portfolio.main.account.login.service.AccountService;
import com.portfolio.main.config.security.jwt.util.TokenUtil;
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
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticationUser(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response,
            HttpServletRequest request) {
        final JwtResponse jwtResponse = accountService.login(loginRequest.getLoginId(), loginRequest.getLoginPw());
        TokenUtil.setTokenToResponse(response, jwtResponse);
        request.setAttribute("token", jwtResponse.getToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        accountService.logout(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegist userRegist) {
        userRegist.validate();
        accountService.signUp(userRegist);
        return ResponseEntity.ok().build();
    }
}
