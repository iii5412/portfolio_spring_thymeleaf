package com.portfolio.main.menu.domain;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.domain.User;
import com.portfolio.main.menu.dto.program.EditProgram;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "program")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "program_name", nullable = false)
    private String programName;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_by", referencedColumnName = "id")
    private User lastUpdatedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public Program(String programName, String url, User modifier) {
        this.programName = programName;
        this.url = url;
        this.lastUpdatedByUser = modifier;
    }

    public Long getId() {
        return id;
    }

    public void edit(EditProgram editProgram, Role role, User user) {
        this.programName = editProgram.getProgramName();
        this.url = editProgram.getUrl();
        this.role = role;
        this.lastUpdatedByUser = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        final Program program = (Program) o;
        return Objects.equals(id, program.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
