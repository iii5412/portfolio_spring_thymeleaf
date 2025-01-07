package com.portfolio.main.domain.model.account;

import com.portfolio.main.domain.model.account.type.RoleCode;
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

    @Transient
    private Integer level = 1;

    public boolean hasUpperRole() {
        return this.upperRole != null;
    }

    public boolean hasChildRoles() {
        return !this.childRoles.isEmpty();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevels(int currentLevel) {
        this.level = currentLevel;
        for(Role childRole : this.childRoles)
            childRole.setLevels(currentLevel + 1);
    }
}
