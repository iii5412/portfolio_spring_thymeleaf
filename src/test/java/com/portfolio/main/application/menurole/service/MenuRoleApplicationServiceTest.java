package com.portfolio.main.application.menurole.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.service.MenuQueryService;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.menurole.dto.SaveMenuRole;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.service.account.role.RoleService;
import com.portfolio.main.presentation.rest.TestAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuRoleApplicationServiceTest {

    @Autowired
    private MenuRoleApplicationService menuRoleApplicationService;
    @Autowired
    private MenuQueryService menuQueryService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TestAuth testAuth;

}
