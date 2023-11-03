package com.portfolio.main.menu.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_id")
    private Menu upperMenu;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "order_num")
    private Long order_num;

    @Column(name = "last_modified_by", nullable = false)
    private String LastModifiedBy;
    @Column(name = "created_at", insertable = false)
    private LocalDateTime createAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "upperMenu", cascade = CascadeType.ALL)
    private List<Menu> subMenus = new ArrayList<>();
}
