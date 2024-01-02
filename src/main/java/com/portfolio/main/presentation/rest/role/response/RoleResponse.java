package com.portfolio.main.presentation.rest.role.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.dto.RoleLevelDto;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import com.portfolio.main.domain.model.account.type.RoleCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoleResponse {
    private Long id;
    private Long upperRoleId;
    private RoleCode roleCode;
    private String roleName;
    private List<RoleResponse> childRoles;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime updatedAt;

    public RoleResponse(RoleDto roleDto) {
        if (roleDto.hasUpperRole()) {
            this.upperRoleId = roleDto.getUpperId();
        }

        this.id = roleDto.getId();
        this.roleCode = roleDto.getRoleCode();
        this.roleName = roleDto.getRoleName();

        if (roleDto.hasChildRoles()) {
            this.childRoles = roleDto.getChildRoles().stream().map(RoleResponse::new).toList();
        }

        this.createAt = roleDto.getCreatedAt();
        this.updatedAt = roleDto.getUpdatedAt();
    }

    public RoleResponse(RoleLevelDto roleLevelDto) {
        this.id = roleLevelDto.getId();
        this.upperRoleId = roleLevelDto.getUpperId();
        this.roleCode = roleLevelDto.getRoleCode();
        this.roleName = roleLevelDto.getRoleName();
        this.createAt = roleLevelDto.getCreatedAt();
        this.updatedAt = roleLevelDto.getUpdatedAt();
    }

}
