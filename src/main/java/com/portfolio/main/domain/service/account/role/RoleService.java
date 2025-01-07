package com.portfolio.main.domain.service.account.role;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.exception.RoleNotFoundException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.infrastructure.repository.mapper.role.RoleMapper;
import com.portfolio.main.domain.repository.account.RoleRepository;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.FindRolesParamDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

//    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
//    public List<RoleMapperDto> findAllFlat() {
//        return roleMapper.findRoles();
//    }

    public List<Role> findFlattenedAboveLevel(Integer level) {
        final List<Role> allFlat = findAllFlat();
        return allFlat.stream().filter(role -> role.getLevel() <= level).toList();
    }

    public Role findByRoleCode(RoleCode roleCode) {
        final Role role = roleRepository.findByRoleCode(roleCode).orElseThrow(RoleNotFoundException::new);
        role.setLevels(1);
        return role;
    }

//    public RoleMapperDto findByRoleCodeFlat(RoleCode roleCode) {
//        final Role role = roleRepository.findByRoleCode(roleCode).orElseThrow(RoleNotFoundException::new);
//        return roles.isEmpty() ? null : roles.get(0);
//    }

//    public RoleMapperDto findByIdFlat(Long id) {
//        final Role role = roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
//        role.setLevels(1);
//        return
//    }

    public List<Role> findAll() {
        final List<Role> roleList = roleRepository.findAll().stream()
                .filter(role -> !role.hasUpperRole())
                .toList();

        roleList.forEach(role -> role.setLevels(1));

        return roleList;
    }

    public Role findById(Long id) {
        final Role role = roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
        role.setLevels(1);
        return role;
    }

    public List<Role> findAllFlat() {
        final List<Role> topRoles = findAll();

        List<Role> flattenedRoles = new ArrayList<>();
        for(Role role : topRoles) {
            addRoleAndDescendants(flattenedRoles, role);
        }

        return flattenedRoles;
    }

    private void addRoleAndDescendants(List<Role> flattenedRoles, Role role){
        flattenedRoles.add(role);

        for(Role childRole : role.getChildRoles()){
            addRoleAndDescendants(flattenedRoles, childRole);
        }
    }
}
