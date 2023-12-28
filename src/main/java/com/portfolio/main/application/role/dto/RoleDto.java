package com.portfolio.main.application.role.dto;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import lombok.Getter;

@Getter
public class RoleDto {
    private Long id;
    private Long upperId;
    private RoleCode roleCode;
    private String roleName;

    public RoleDto(Role role) {
        this.id = role.getId();

        if(role.hasUpperRole())
            this.upperId = role.getUpperRole().getId();

        this.roleCode = role.getRoleCode();
        this.roleName = role.getRoleName();
    }
}
