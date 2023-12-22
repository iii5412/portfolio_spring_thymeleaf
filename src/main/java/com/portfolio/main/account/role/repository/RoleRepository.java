package com.portfolio.main.account.role.repository;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.role.service.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleCode(RoleCode roleCode) ;

}
