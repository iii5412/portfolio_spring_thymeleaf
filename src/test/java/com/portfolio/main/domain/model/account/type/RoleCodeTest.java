package com.portfolio.main.domain.model.account.type;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleCodeTest {

    @Test
    void testGetLevel() {
        assertEquals(1, RoleCode.ROLE_ADMIN.getLevel());
        assertEquals(2, RoleCode.ROLE_USER.getLevel());
        assertEquals(3, RoleCode.ROLE_GUEST.getLevel());
    }

    @Test
    void testGetHighestAuthority() {
        GrantedAuthority adminAuthority = mock(GrantedAuthority.class);
        GrantedAuthority userAuthority = mock(GrantedAuthority.class);
        GrantedAuthority guestAuthority = mock(GrantedAuthority.class);
        GrantedAuthority anonymousAuthority = mock(GrantedAuthority.class);

        when(adminAuthority.getAuthority()).thenReturn("ROLE_ADMIN");
        when(userAuthority.getAuthority()).thenReturn("ROLE_USER");
        when(guestAuthority.getAuthority()).thenReturn("ROLE_GUEST");
        when(anonymousAuthority.getAuthority()).thenReturn("ROLE_ANONYMOUS");

        Collection<GrantedAuthority> authorities1 = Arrays.asList(adminAuthority, userAuthority, guestAuthority);
        Collection<GrantedAuthority> authorities2 = Arrays.asList(userAuthority, guestAuthority);
        Collection<GrantedAuthority> authorities3 = Arrays.asList(guestAuthority);
        Collection<GrantedAuthority> authorities4 = Arrays.asList(anonymousAuthority);

        assertEquals(RoleCode.ROLE_ADMIN, RoleCode.getHighestAuthority(authorities1));
        assertEquals(RoleCode.ROLE_USER, RoleCode.getHighestAuthority(authorities2));
        assertEquals(RoleCode.ROLE_GUEST, RoleCode.getHighestAuthority(authorities3));
        assertEquals(RoleCode.ROLE_GUEST, RoleCode.getHighestAuthority(authorities4)); // ROLE_ANONYMOUS는 ROLE_GUEST로 매핑
        assertNull(RoleCode.getHighestAuthority(List.of())); // 빈 리스트인 경우
    }

    @Test
    void testGetHigherAndSelfRoles() {
        List<RoleCode> adminRoles = RoleCode.ROLE_ADMIN.getHigherAndSelfRoles();
        List<RoleCode> userRoles = RoleCode.ROLE_USER.getHigherAndSelfRoles();
        List<RoleCode> guestRoles = RoleCode.ROLE_GUEST.getHigherAndSelfRoles();

        // 각 RoleCode는 자신과 같은 레벨 또는 더 높은 레벨만 포함해야 함
        assertEquals(List.of(RoleCode.ROLE_ADMIN), adminRoles);
        assertEquals(List.of(RoleCode.ROLE_ADMIN, RoleCode.ROLE_USER), userRoles);
        assertEquals(List.of(RoleCode.ROLE_ADMIN, RoleCode.ROLE_USER, RoleCode.ROLE_GUEST), guestRoles);
    }
}
