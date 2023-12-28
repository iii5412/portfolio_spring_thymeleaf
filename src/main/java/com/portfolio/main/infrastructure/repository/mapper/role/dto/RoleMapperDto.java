package com.portfolio.main.infrastructure.repository.mapper.role.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoleMapperDto {
    private Long id;
    private Long upperId;
    private String roleCode;
    private String roleName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer level;

    @Override
    public String toString() {
        return "RoleMapperDto{" +
                "id=" + id +
                ", upperId=" + upperId +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", level=" + level +
                '}';
    }
}
