package com.portfolio.main.menu.dto.menu;

import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.service.MenuType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMenu {
    private Long upperId;
    private String menuName;
    private MenuType menuType;
    private Long orderNum;
    private String lastModifiedBy;

    public CreateMenu(String menuName, MenuType menuType, Long orderNum, String lastModifiedBy) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.lastModifiedBy = lastModifiedBy;
    }

    public CreateMenu(Long upperId, String menuName, MenuType menuType, Long orderNum, String lastModifiedBy) {
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.lastModifiedBy = lastModifiedBy;
    }
}
