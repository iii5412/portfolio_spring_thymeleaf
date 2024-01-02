package com.portfolio.main.application.login.dto;

import com.portfolio.main.presentation.rest.account.login.request.SignUpRequest;
import lombok.Getter;

@Getter
public class UserCreateDto {
    private String loginId;
    private String username;
    private String loginPw;

    public UserCreateDto(SignUpRequest signUpRequest) {
        this.loginId = signUpRequest.getLoginId();
        this.username = signUpRequest.getUsername();
        this.loginPw = signUpRequest.getLoginPw1();
    }
}
