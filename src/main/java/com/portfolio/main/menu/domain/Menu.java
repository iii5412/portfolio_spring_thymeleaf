package com.portfolio.main.menu.domain;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.menu.dto.menu.EditMenu;
import com.portfolio.main.menu.service.MenuType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Long orderNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by", referencedColumnName = "id")
    private User lastModifiedByUser;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "upperMenu", cascade = CascadeType.ALL)
    private List<Menu> subMenus = new ArrayList<>();

    public Menu(String menuName, MenuType menuType, Long orderNum, User lastModifiedBy) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.lastModifiedByUser = lastModifiedBy;
    }

    public void addSubMenu(Menu subMenu){
        this.subMenus.add(subMenu);
    }

    public void changeSubMenus(List<Menu> subMenus) {
        this.subMenus = new ArrayList<>(subMenus);
    }

    public boolean hasSubMenus() {
        return !this.subMenus.isEmpty();
    }

    public boolean hasUpperMenu() {
        return upperMenu != null;
    }

    public boolean hasProgram() {
        return this.program != null;
    }

    public void setUpperMenu(Menu newUpperMenu) {
        if(this.upperMenu != null && !this.upperMenu.equals(newUpperMenu)){
            this.upperMenu.removeSubMenu(this);
        }

        this.upperMenu = newUpperMenu;

        if(newUpperMenu != null) {
            newUpperMenu.addSubMenu(this);
        }
    }

    public void removeSubMenu(Menu removeSubMenu) {
        this.subMenus.remove(removeSubMenu);
    }
    public void clearSubMenu() {
        this.subMenus.clear();
    }
    public void edit(EditMenu editMenu, Menu upperMenu, Program program, User editUser) {
        this.menuName = editMenu.getMenuName();
        this.menuType = editMenu.getMenuType();
        this.orderNum = editMenu.getOrderNum();
        this.lastModifiedByUser = editUser;

        if(!Objects.equals(this.upperMenu, upperMenu))
            setUpperMenu(upperMenu);

        if(!Objects.equals(this.program, program))
            this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        final Menu menu = (Menu) o;
        return Objects.equals(id, menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
