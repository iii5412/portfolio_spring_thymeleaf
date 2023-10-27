package com.portfolio.main.account.web;

import com.portfolio.main.account.login.dto.JwtResponse;
import com.portfolio.main.account.login.dto.LoginRequest;
import com.portfolio.main.account.login.dto.UserRegist;
import com.portfolio.main.account.login.exception.InvalidLoginId;
import com.portfolio.main.account.login.exception.InvalidLoginPassword;
import com.portfolio.main.account.login.exception.InvalidRegistUser;
import com.portfolio.main.account.login.service.AccountService;
import com.portfolio.main.security.jwt.util.TokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        } catch (InvalidLoginId | InvalidLoginPassword e) {
            return ResponseEntity.status(e.getStatusCode()).body(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        accountService.logout(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegist userRegist) {
        try {
            userRegist.validate();
            accountService.signUp(userRegist);
            return ResponseEntity.ok().build();
        } catch (InvalidRegistUser e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
