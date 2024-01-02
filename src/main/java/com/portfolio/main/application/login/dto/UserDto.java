package com.portfolio.main.application.login.dto;

import com.portfolio.main.domain.model.account.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserDto {
    private Long id;
    private String loginId;
    private String userName;
    private LocalDateTime createdAt;
    private List<UserRoleDto> userRoles = new ArrayList<>();

    public UserDto(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.userName = user.getUserName();
        this.createdAt = user.getCreatedAt();

        if(!user.getUserRoles().isEmpty()) {
            this.userRoles = user.getUserRoles().stream().map(UserRoleDto::new).toList();
        }
    }

    public List<String> getRoleCodes() {
        return userRoles.stream()
                .map(userRole -> userRole.getRoleCode().name())
                .collect(Collectors.toList());
    }
}
