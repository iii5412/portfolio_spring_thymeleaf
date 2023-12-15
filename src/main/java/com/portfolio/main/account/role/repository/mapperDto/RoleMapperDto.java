package com.portfolio.main.account.role.repository.mapperDto;

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
    private Long level;
}
