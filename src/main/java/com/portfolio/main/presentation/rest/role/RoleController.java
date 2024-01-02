package com.portfolio.main.presentation.rest.role;

import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.dto.RoleLevelDto;
import com.portfolio.main.application.role.service.RoleApplicationService;
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

    private final RoleApplicationService roleApplicationService;

    public RoleController(RoleApplicationService roleApplicationService) {
        this.roleApplicationService = roleApplicationService;
    }

    @GetMapping("")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        final List<RoleDto> allRoles = roleApplicationService.findAll();
        final List<RoleResponse> responses = allRoles.stream().map(RoleResponse::new).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/flat")
    public ResponseEntity<List<RoleResponse>> getAllRolesFlat() {
        final List<RoleLevelDto> allRolesFlat = roleApplicationService.findAllFlat();
        final List<RoleResponse> responses = allRolesFlat.stream().map(RoleResponse::new).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/roleCodeNames")
    public ResponseEntity<List<RoleCodeNameResponse>> getAllRolesCodeNames() {
        final List<RoleLevelDto> allRoles = roleApplicationService.findAllFlat();
        final List<RoleCodeNameResponse> responses = allRoles.stream().map(RoleCodeNameResponse::new).toList();

        return ResponseEntity.ok(responses);
    }

}
