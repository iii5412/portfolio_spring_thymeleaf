package com.portfolio.main.application.menurole.dto;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.domain.model.menu.MenuRole;
import lombok.Getter;

@Getter
public class MenuRoleDto {
    private MenuDto menuDto;
    private RoleDto roleDto;

    public MenuRoleDto(MenuRole menuRole) {
        this.menuDto = new MenuDto(menuRole.getMenu());
        this.roleDto = new RoleDto(menuRole.getRole());
    }
}
