package com.portfolio.main.application.menu.dto;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
public class MenuDto {
    private Long id;
    private MenuDto upperMenu;
    private MenuType menuType;
    private Long orderNum;
    private ProgramDto program;
    private List<RoleDto> roles = new ArrayList<>();
    private String menuName;
    private UserDto lastModifiedByUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MenuDto> subMenus = new ArrayList<>();

    public MenuDto(Menu menu) {
        this.id = menu.getId();

        if(menu.hasUpperMenu()){
            this.upperMenu = new MenuDto(menu.getUpperMenu());
        }

        this.menuType = menu.getMenuType();
        this.orderNum = menu.getOrderNum();

        if(menu.hasProgram())
            this.program = new ProgramDto(menu.getProgram());

        if(menu.getMenuRoles() != null) {
            this.roles = menu.getMenuRoles().stream()
                    .map(MenuRole::getRole)
                    .map(RoleDto::new)
                    .toList();
        }

        this.menuName = menu.getMenuName();
        this.lastModifiedByUser = new UserDto(menu.getLastModifiedByUser());
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getUpdatedAt();
    }

    public MenuDto (MenuDto menuDto) {
        this.id = menuDto.getId();
        this.upperMenu = menuDto.getUpperMenu();
        this.menuType = menuDto.getMenuType();
        this.orderNum = menuDto.getOrderNum();
        this.program = menuDto.getProgram();
        this.roles = menuDto.getRoles();
        this.menuName = menuDto.getMenuName();
        this.lastModifiedByUser = menuDto.getLastModifiedByUser();
        this.createdAt = menuDto.getCreatedAt();
        this.updatedAt = menuDto.getUpdatedAt();
    }

    public void setSubMenus(List<MenuDto> subMenus) {
        this.subMenus = new ArrayList<>(subMenus);
    }

    public boolean hasUpperMenu() {
        return this.upperMenu != null;
    }

    public boolean hasSubMenus() {
        return !this.subMenus.isEmpty();
    }

    public boolean hasProgram() {
        return this.program != null;
    }

    public boolean isFolder() {
        return this.menuType.equals(MenuType.FOLDER);
    }

    public void clearSubMenu() {
        this.subMenus.clear();
    }

    public void addSubMenu(MenuDto subMenu){
        this.subMenus.add(subMenu);
        this.subMenus.sort(Comparator.comparing(MenuDto::getOrderNum));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        final MenuDto menu = (MenuDto) obj;
        return Objects.equals(id, menu.getId());
    }
}
