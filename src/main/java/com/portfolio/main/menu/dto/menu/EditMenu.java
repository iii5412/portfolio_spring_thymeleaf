package com.portfolio.main.menu.dto.menu;

import com.portfolio.main.menu.service.MenuType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditMenu {
    private Long upperId;
    private String menuName;
    private MenuType menuType;
    private Long orderNum;
    private Long programId;
    private String editUserLoginId;

    public EditMenu(Long upperId, String menuName, MenuType menuType, Long orderNum, Long programId) {
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.programId = programId;
    }
}
