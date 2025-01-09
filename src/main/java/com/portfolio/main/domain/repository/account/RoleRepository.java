package com.portfolio.main.domain.repository.account;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Role Entity에 대한 Repository
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleCode(RoleCode roleCode) ;
}
