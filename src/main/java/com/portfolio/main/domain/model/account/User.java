package com.portfolio.main.domain.model.account;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "login_pw")
    private String loginPw;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRole> userRoles = new ArrayList<>();

    public User(String loginId, String loginPw, String userName, List<UserRole> userRoles) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.userName = userName;

        this.userRoles = Objects.requireNonNullElseGet(userRoles, ArrayList::new);
    }

    public User(Long id, String loginId, String loginPw, String userName, List<UserRole> userRoles) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.userName = userName;
        this.userRoles = userRoles;
    }

    public void addUserRole(UserRole userRole) {
        this.userRoles.add(userRole);
    }

    @Override
    public boolean equals(Object obj) {
        final User targetUser = (User) obj;
        return this.id.equals(targetUser.getId());
    }
}
