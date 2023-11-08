package com.portfolio.main.menu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "created_at", insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "last_updated_by", nullable = false)
    private Long lastUpdatedBy;

    public Program(String programName, String url) {
        this.programName = programName;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

}
