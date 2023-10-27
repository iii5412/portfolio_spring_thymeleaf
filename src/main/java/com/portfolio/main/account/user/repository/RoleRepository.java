package com.portfolio.main.account.user.repository;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.user.service.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleCode(RoleCode roleCode);
}
