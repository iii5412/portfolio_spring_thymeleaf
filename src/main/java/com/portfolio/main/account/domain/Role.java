package com.portfolio.main.account.domain;

import com.portfolio.main.account.role.service.RoleCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_id")
    private Role upperRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_code")
    private RoleCode roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "upperRole", cascade = CascadeType.ALL)
    private List<Role> childRoles = new ArrayList<>();

    public boolean hasUpperRole() {
        return this.upperRole != null;
    }

    public boolean hasChildRoles() {
        return !this.childRoles.isEmpty();
    }

}
