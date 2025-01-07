package com.portfolio.main.application.role.dto;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RoleDto {
    private Long id;
    private Long upperId;
    private RoleCode roleCode;
    private String roleName;
    private Integer level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RoleDto> childRoles = new ArrayList<>();


    public RoleDto(Role role) {
        this.id = role.getId();

        if(role.hasUpperRole())
            this.upperId = role.getUpperRole().getId();

        this.roleCode = role.getRoleCode();
        this.roleName = role.getRoleName();
        this.level = role.getLevel();
        this.createdAt = role.getCreatedAt();
        this.updatedAt = role.getUpdatedAt();

        if(role.hasChildRoles()){
            this.childRoles = role.getChildRoles().stream().map(RoleDto::new).toList();
        }
    }

    public boolean hasUpperRole(){
        return this.upperId != null;
    }

    public boolean hasChildRoles() {
        return !this.childRoles.isEmpty();
    }

}
