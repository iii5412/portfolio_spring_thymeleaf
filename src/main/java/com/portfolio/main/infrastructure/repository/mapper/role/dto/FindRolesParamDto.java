package com.portfolio.main.infrastructure.repository.mapper.role.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindRolesParamDto {
    private Integer level;
    private Long id;
    private String roleCode;

    @Builder
    public FindRolesParamDto(Integer level, Long id, String roleCode) {
        this.level = level;
        this.id = id;
        this.roleCode = roleCode;
    }
}
