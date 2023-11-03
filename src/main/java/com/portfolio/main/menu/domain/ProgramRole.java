package com.portfolio.main.menu.domain;

import com.portfolio.main.account.domain.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "program_role")
public class ProgramRole {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
