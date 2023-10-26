package com.portfolio.main.account.web;

import com.portfolio.main.account.dto.JwtResponse;
import com.portfolio.main.account.dto.LoginRequest;
import com.portfolio.main.account.dto.UserRegist;
import com.portfolio.main.account.service.AccountService;
import com.portfolio.main.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.security.jwt.util.TokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticationUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            final JwtResponse jwtResponse = accountService.login(loginRequest.getLoginId(), loginRequest.getLoginPw());
            TokenUtil.setTokenToResponse(response, jwtResponse);
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        final Cookie jwtCookie = new Cookie(JwtAuthenticationToken.TOKEN_NAME, null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegist userRegist) {
        userRegist.validate();
        accountService.signUp(userRegist);
        return ResponseEntity.ok().build();
    }
}
