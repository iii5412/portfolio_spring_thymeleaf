package com.portfolio.main.domain.service.account.userrole;

import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.account.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    public UserRole createUserRole(User user, Role role) {
        return new UserRole(user, role);
    }
}
