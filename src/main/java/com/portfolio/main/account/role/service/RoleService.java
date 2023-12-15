package com.portfolio.main.account.role.service;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.role.repository.RoleMapper;
import com.portfolio.main.account.role.repository.RoleRepository;
import com.portfolio.main.account.role.repository.mapperDto.RoleMapperDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    public List<RoleMapperDto> findAllRolesFlat() {
        return roleMapper.findAllRoles();
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll().stream()
                .filter(role -> !role.hasUpperRole())
                .toList();
    }
}
