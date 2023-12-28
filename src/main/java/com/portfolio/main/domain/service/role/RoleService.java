package com.portfolio.main.domain.service.role;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.exception.RoleNotFoundException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.infrastructure.repository.mapper.role.RoleMapper;
import com.portfolio.main.domain.repository.account.RoleRepository;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.FindRolesParamDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    public List<RoleMapperDto> findAllFlat() {
        return roleMapper.findRoles();
    }

    public List<RoleMapperDto> findFlattenedAboveLevel(Integer level) {
        return roleMapper.findRoles(FindRolesParamDto.builder().level(level).build());
    }

    public Role findByRoleCode(RoleCode roleCode) {
        return roleRepository.findByRoleCode(roleCode).orElseThrow(RoleNotFoundException::new);
    }

    public RoleMapperDto findByRoleCodeFlat(RoleCode roleCode) {
        final List<RoleMapperDto> roles = roleMapper.findRoles(FindRolesParamDto.builder().roleCode(roleCode.name()).build());
        return roles.isEmpty() ? null : roles.get(0);
    }

    public RoleMapperDto findByIdFlat(Long id) {
        final List<RoleMapperDto> roles = roleMapper.findRoles(FindRolesParamDto.builder().id(id).build());
        return roles.isEmpty() ? null : roles.get(0);
    }

    public List<Role> findAll() {
        return roleRepository.findAll().stream()
                .filter(role -> !role.hasUpperRole())
                .toList();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    }
}
