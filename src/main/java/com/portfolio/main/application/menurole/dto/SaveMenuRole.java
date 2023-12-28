package com.portfolio.main.application.menurole.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveMenuRole {
    private Long menuId;
    private Long roleId;

    public SaveMenuRole(Long menuId, Long roleId) {
        this.menuId = menuId;
        this.roleId = roleId;
    }
}
