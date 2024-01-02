package com.portfolio.main.application.login.dto;

import com.portfolio.main.domain.model.account.UserRole;
import com.portfolio.main.domain.model.account.type.RoleCode;
import lombok.Getter;

@Getter
public class UserRoleDto {
    private Long userId;
    private String loginId;
    private String userName;
    private Long roleId;
    private RoleCode roleCode;

    public UserRoleDto(UserRole userRole) {
        this.userId = userRole.getUser().getId();
        this.loginId = userRole.getUser().getLoginId();
        this.userName = userRole.getUser().getUserName();
        this.roleId = userRole.getRole().getId();
        this.roleCode = userRole.getRole().getRoleCode();

    }
}
