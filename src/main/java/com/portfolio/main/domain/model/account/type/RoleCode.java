package com.portfolio.main.domain.model.account.type;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 주어진 권한(authorities) 목록에서 가장 높은 권한을 가진 RoleCode를 반환합니다.
     *
     * @param authorities 권한 목록 (GrantedAuthority의 Collection)
     * @return 가장 높은 권한을 가진 RoleCode, 권한 목록이 비어있거나 유효한 RoleCode가 없는 경우 null 반환
     */
    public static RoleCode getHighestAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                // ROLE_ANONYMOUS를 ROLE_GUEST로 매핑
                .map(role -> role.equals("ROLE_ANONYMOUS") ? "ROLE_GUEST" : role)
                .min(Comparator.comparingInt(roleCode -> RoleCode.valueOf(roleCode).getLevel()))
                .map(RoleCode::valueOf)
                .orElse(null);
    }

    /**
     * 주어진 RoleCode 컬렉션에서 가장 높은 권한을 가진 RoleCode를 반환합니다.
     *
     * @param roleCodes RoleCode 컬렉션
     * @return 가장 높은 권한을 가진 RoleCode, 컬렉션이 비어있거나 유효한 RoleCode가 없는 경우 null 반환
     */
    public static RoleCode getHighestRoleCodes(Collection<RoleCode> roleCodes) {
        return roleCodes.stream()
                .min(Comparator.comparingInt(RoleCode::getLevel))
                .orElse(null);
    }


    /**
     *
     * 현재 RoleCode와 같거나 더 높은 권한을 가진 RoleCode 목록을 반환합니다.
     *
     * @return 현재 RoleCode와 같거나 더 높은 권한을 가진 RoleCode 목록
     */
    public List<RoleCode> getHigherAndSelfRoles() {
        return Arrays.stream(RoleCode.values())
                // 레벨이 낮을수록 높은 권한을 가짐
                .filter(roleCode -> roleCode.getLevel() <= this.level)
                .collect(Collectors.toList());
    }
}
