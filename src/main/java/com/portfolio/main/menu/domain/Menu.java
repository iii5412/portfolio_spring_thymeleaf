package com.portfolio.main.menu.domain;

import com.portfolio.main.menu.service.MenuType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "menu_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @Column(name = "order_num")
    private Long order_num;

    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @Column(name = "created_at", insertable = false)
    private LocalDateTime createAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "upperMenu", cascade = CascadeType.ALL)
    private List<Menu> subMenus = new ArrayList<>();

    public Menu(String menuName, MenuType menuType, Long order_num, String lastModifiedBy) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.order_num = order_num;
        this.lastModifiedBy = lastModifiedBy;
    }

    public void addSubMenu(Menu subMenu){
        this.subMenus.add(subMenu);
    }

    public void changeSubMenus(List<Menu> subMenus) {
        this.subMenus = new ArrayList<>(subMenus);
    }

    public boolean hasSubMenus() {
        return this.subMenus.size() > 0;
    }

    public void setUpperMenu(Menu upperMenu) {
        this.upperMenu = upperMenu;
        this.upperMenu.addSubMenu(this);
    }
}
