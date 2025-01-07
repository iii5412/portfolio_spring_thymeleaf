package com.portfolio.main.application.menu.dto;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class MenuManageDto {
    private Long id;
//    private MenuManageDto upperMenu;
    private Long upperMenuId;
    private MenuType menuType;
    private RoleCode roleCode;
    private Long orderNum;
    private ProgramDto program;
    private String menuName;
    private UserDto lastModifiedByUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MenuManageDto> subMenus = new ArrayList<>();


    public MenuManageDto(MenuDto menuDto) {
        this.id = menuDto.getId();

        if (menuDto.hasUpperMenu()) {
//            this.upperMenu = new MenuManageDto(menuDto.getUpperMenu());
            this.upperMenuId = menuDto.getUpperMenuId();
        }

        if (menuDto.hasSubMenus()) {
            final List<MenuManageDto> subMenus = menuDto.getSubMenus().stream().map(MenuManageDto::new).toList();
            this.setSubMenus(subMenus);
        }


        if (menuDto.getTopRole() != null) {
            this.roleCode = menuDto.getTopRole().getRoleCode();
        }

    }

    public void clearSubMenu() {
        this.subMenus.clear();
    }

    public boolean hasSubMenus() {
        return this.subMenus.isEmpty();
    }

    public boolean hasProgram() {
        return this.program != null;
    }

    public boolean hasUpperMenu() {
        return this.upperMenuId != null;
    }

    public void addSubMenu(MenuManageDto suMenuManageDto) {
        this.subMenus.add(suMenuManageDto);
        this.subMenus.sort(Comparator.comparing(MenuManageDto::getOrderNum));
    }
}
