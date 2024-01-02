package com.portfolio.main.presentation.rest.role.response;

import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.dto.RoleLevelDto;
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

    public RoleCodeNameResponse(RoleDto roleDto) {
        this.id = roleDto.getId();
        this.roleCode = roleDto.getRoleCode().name();
        this.roleName = roleDto.getRoleName();
    }

    public RoleCodeNameResponse(RoleLevelDto roleLevelDto) {
        this.id = roleLevelDto.getId();
        this.roleCode = roleLevelDto.getRoleCode().name();
        this.roleName = roleLevelDto.getRoleName();
    }
}
