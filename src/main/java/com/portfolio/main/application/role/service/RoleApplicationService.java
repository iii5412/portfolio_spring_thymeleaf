package com.portfolio.main.application.role.service;

import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.dto.RoleLevelDto;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.service.account.role.RoleService;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleApplicationService {
    private final RoleService roleService;

    public RoleApplicationService(RoleService roleService) {
        this.roleService = roleService;
    }

    public List<RoleDto> findAll() {
        return roleService.findAll().stream().map(RoleDto::new).toList();
    }

    public RoleDto findById(Long id) {
        final Role byId = roleService.findById(id);
        return new RoleDto(byId);
    }

    public List<RoleDto> findAllFlat() {
        return roleService.findAllFlat().stream().map(RoleDto::new).toList();
    }

    public List<RoleDto> findFlattenedAboveLevel(Integer level) {
        return roleService.findFlattenedAboveLevel(level).stream().map(RoleDto::new).toList();
    }

    public RoleDto findByRoleCode(RoleCode roleCode) {
        final Role role = roleService.findByRoleCode(roleCode);
        return new RoleDto(role);
    }
}
