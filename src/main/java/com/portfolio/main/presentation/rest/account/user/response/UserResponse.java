package com.portfolio.main.presentation.rest.account.user.response;

import com.portfolio.main.application.login.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String loginId;
    private String userName;
    private LocalDateTime createdAt;
    private List<String> roleCodes;

    public UserResponse(UserDto userDto) {
        this.id = userDto.getId();
        this.loginId = userDto.getLoginId();
        this.userName = userDto.getUserName();
        this.createdAt = userDto.getCreatedAt();
        this.roleCodes = userDto.getRoleCodes();
    }

}

