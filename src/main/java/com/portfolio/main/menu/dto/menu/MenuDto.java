package com.portfolio.main.menu.dto.menu;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.mapperdto.MenuMapperDto;
import com.portfolio.main.menu.service.MenuType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuDto {
    private Long id;
    private Long upperMenuId;
    private MenuType menuType;
    private Long orderNum;
    private Program program;
    private String menuName;
    private User lastModifiedByUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MenuDto> subMenus = new ArrayList<>();

    public MenuDto(Menu menu) {
        this.id = menu.getId();

        if(menu.hasUpperMenu())
            this.upperMenuId = menu.getUpperMenu().getId();

        this.menuType = menu.getMenuType();
        this.orderNum = menu.getOrderNum();

        if(menu.hasProgram())
            this.program = menu.getProgram();

        this.menuName = menu.getMenuName();
        this.lastModifiedByUser = menu.getLastModifiedByUser();
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getUpdatedAt();
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus.stream().map(MenuDto::new).toList();
    }

    public void addSubMenu(Menu menu) {
        this.subMenus.add(new MenuDto(menu));
    }

    public boolean hasUpperMenu() {
        return this.upperMenuId != null;
    }

    public boolean hasSubMenus() {
        return !this.subMenus.isEmpty();
    }

    public boolean hasProgram() {
        return this.program != null;
    }
}
