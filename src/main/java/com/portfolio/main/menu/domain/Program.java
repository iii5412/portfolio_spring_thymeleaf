package com.portfolio.main.menu.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "program")
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


}
