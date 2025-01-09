package com.portfolio.main.domain.model.schedule;

import com.portfolio.main.domain.model.account.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자와 연결되고 이름, 설명, 생성 및 업데이트 타임스탬프를 포함하는 카테고리 엔터티를 나타냅니다.
 */
@Entity
@Table(name = "schedule_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
