package com.portfolio.main.account.role.repository;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.role.service.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleCode(RoleCode roleCode);

}
