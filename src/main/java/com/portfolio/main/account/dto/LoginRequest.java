package com.portfolio.main.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String loginId;
    private String loginPw;
}
