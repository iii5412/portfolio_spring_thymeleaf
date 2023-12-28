package com.portfolio.main.presentation.rest.role;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import com.portfolio.main.domain.service.role.RoleService;
import com.portfolio.main.presentation.rest.role.response.RoleCodeNameResponse;
import com.portfolio.main.presentation.rest.role.response.RoleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        final List<Role> allRoles = roleService.findAll();
        final List<RoleResponse> responses = allRoles.stream().map(RoleResponse::new).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/flat")
    public ResponseEntity<List<RoleResponse>> getAllRolesFlat() {
        final List<RoleMapperDto> allRolesFlat = roleService.findAllFlat();
        final List<RoleResponse> responses = allRolesFlat.stream().map(RoleResponse::new).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/roleCodeNames")
    public ResponseEntity<List<RoleCodeNameResponse>> getAllRolesCodeNames() {
        final List<RoleMapperDto> allRoles = roleService.findAllFlat();
        final List<RoleCodeNameResponse> responses = allRoles.stream().map(RoleCodeNameResponse::new).toList();

        return ResponseEntity.ok(responses);
    }

}
