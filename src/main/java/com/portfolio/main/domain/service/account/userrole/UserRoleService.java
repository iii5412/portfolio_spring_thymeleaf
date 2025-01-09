package com.portfolio.main.domain.service.account.userrole;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.account.UserRole;
import org.springframework.stereotype.Service;

/**
 * 사용자 역할을 관리하는 역할을 담당하는 서비스 클래스.
 */
@Service
public class UserRoleService {
    public UserRole createUserRole(User user, Role role) {
        return new UserRole(user, role);
    }
}
