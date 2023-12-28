package com.portfolio.main.domain.model.menu;

import com.portfolio.main.domain.model.account.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "menu_role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuRole {

    @EmbeddedId
    private MenuRoleId id;

    @ManyToOne
    @MapsId("menuId")
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public MenuRole(MenuRoleId id, Menu menu, Role role) {
        this.id = id;
        this.menu = menu;
        this.role = role;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MenuRoleId implements Serializable {
        @Column(name = "menu_id")
        private Long menuId;

        @Column(name = "role_id")
        private Long roleId;

        public MenuRoleId(Long menuId, Long roleId) {
            this.menuId = menuId;
            this.roleId = roleId;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) return true;
            if(obj == null || getClass() != obj.getClass()) return false;
            MenuRoleId that = (MenuRoleId) obj;
            return Objects.equals(menuId, that.getMenuId()) &&
                    Objects.equals(roleId, that.getRoleId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(menuId, roleId);
        }

        @Override
        public String toString() {
            return "MenuRoleId{" +
                    "menuId=" + menuId +
                    ", roleId=" + roleId +
                    '}';
        }
    }

}
