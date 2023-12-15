package com.portfolio.main.account.user.service;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.role.repository.mapperDto.RoleMapperDto;
import com.portfolio.main.account.role.service.RoleService;
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
        final List<RoleMapperDto> all = roleService.findAllRolesFlat();
        assertFalse(all.isEmpty());
    }

    @Test
    void findAllRoles() {
        final List<Role> allRoles = roleService.findAllRoles();
        assertEquals(1, allRoles.size());
    }
}
