package com.portfolio.main.presentation.rest.role.response;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleCodeNameResponse {
    private Long id;
    private String roleCode;
    private String roleName;

    public RoleCodeNameResponse(Role role) {
        this.id = role.getId();
        this.roleCode = role.getRoleCode().name();
        this.roleName = role.getRoleName();
    }

    public RoleCodeNameResponse(RoleMapperDto role) {
        this.id = role.getId();
        this.roleCode = role.getRoleCode();
        this.roleName = role.getRoleName();
    }
}
