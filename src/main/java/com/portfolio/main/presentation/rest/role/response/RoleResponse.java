package com.portfolio.main.presentation.rest.role.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    public RoleResponse(Role role) {
        if (role.hasUpperRole()) {
            this.upperRoleId = role.getUpperRole().getId();
        }

        this.id = role.getId();
        this.roleCode = role.getRoleCode();
        this.roleName = role.getRoleName();

        if (role.hasChildRoles()) {
            this.childRoles = role.getChildRoles().stream().map(RoleResponse::new).toList();
        }

        this.createAt = role.getCreatedAt();
        this.updatedAt = role.getUpdatedAt();
    }

    public RoleResponse(RoleMapperDto roleMapperDto) {
        this.id = roleMapperDto.getId();
        this.upperRoleId = roleMapperDto.getUpperId();
        this.roleCode = RoleCode.valueOf(roleMapperDto.getRoleCode());
        this.roleName = roleMapperDto.getRoleName();
        this.createAt = roleMapperDto.getCreatedAt();
        this.updatedAt = roleMapperDto.getUpdatedAt();
    }

}
