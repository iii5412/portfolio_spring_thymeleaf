package com.portfolio.main.account.role.service;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.role.exception.NotFoundRoleException;
import com.portfolio.main.account.role.repository.RoleMapper;
import com.portfolio.main.account.role.repository.RoleRepository;
import com.portfolio.main.account.role.dto.mapperdto.RoleMapperDto;
import com.portfolio.main.account.role.repository.dto.FindRolesParamDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return roleRepository.findByRoleCode(roleCode).orElseThrow(NotFoundRoleException::new);
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

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }
}
