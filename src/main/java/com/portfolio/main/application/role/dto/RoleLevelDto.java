package com.portfolio.main.application.role.dto;

import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoleLevelDto {
    private Long id;
    private Long upperId;
    private RoleCode roleCode;
    private String roleName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer level;

    public RoleLevelDto(RoleMapperDto roleMapperDto) {
        this.id = roleMapperDto.getId();
        this.upperId = roleMapperDto.getUpperId();
        this.roleCode = RoleCode.valueOf(roleMapperDto.getRoleCode());
        this.roleName = roleMapperDto.getRoleName();
        this.createdAt = roleMapperDto.getCreatedAt();
        this.updatedAt = roleMapperDto.getUpdatedAt();
        this.level = roleMapperDto.getLevel();
    }
}
