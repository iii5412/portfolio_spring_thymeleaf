package com.portfolio.main.domain.model.account.type;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Comparator;

public enum RoleCode {
    ROLE_ADMIN(1),
    ROLE_USER(2),
    ROLE_GUEST(3);

    private final int level;

    RoleCode(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static RoleCode getHighestAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .min(Comparator.comparingInt(roleCode -> RoleCode.valueOf(roleCode).getLevel()))
                .map(RoleCode::valueOf)
                .orElse(null);
    }
}
