package com.portfolio.main.application.menu.dto;

import com.portfolio.main.domain.model.account.type.RoleCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuManageDto extends MenuDto {
    private RoleCode roleCode;

    public MenuManageDto(MenuDto menuDto, RoleCode roleCode) {
        super(menuDto);
        this.setSubMenus(menuDto.getSubMenus());
        this.roleCode = roleCode;
    }

    public MenuManageDto(MenuManageDto menuManageDto) {
        super(menuManageDto);
        this.setSubMenus(menuManageDto.getSubMenus());
        this.roleCode = menuManageDto.getRoleCode();
    }
}
