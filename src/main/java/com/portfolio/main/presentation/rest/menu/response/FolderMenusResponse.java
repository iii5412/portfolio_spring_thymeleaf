package com.portfolio.main.presentation.rest.menu.response;

import com.portfolio.main.application.menu.dto.MenuDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FolderMenusResponse {
    private Long id;
    private String menuName;
    private Long upperId;
    private Long orderNum;
    private List<FolderMenusResponse> subMenus = new ArrayList<>();


    public FolderMenusResponse(MenuDto menuDto) {
        this.id = menuDto.getId();
        this.menuName = menuDto.getMenuName();
        this.orderNum = menuDto.getOrderNum();

        if (menuDto.hasUpperMenu()) {
            this.upperId = menuDto.getUpperMenuId();
        }

        if (menuDto.hasSubMenus()) {
            this.subMenus = menuDto.getSubMenus().stream().map(FolderMenusResponse::new).toList();
        }
    }
}
