package com.portfolio.main.domain.service.role;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import com.portfolio.main.domain.service.account.role.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleServiceTest {

    @Autowired
    private RoleService roleService;


    @Test
    void findAllRolesFlat() {
        final List<RoleMapperDto> all = roleService.findAllFlat();
        assertFalse(all.isEmpty());
    }

    @Test
    void findAllRoles() {
        final List<Role> allRoles = roleService.findAll();
        assertEquals(1, allRoles.size());
    }
}
