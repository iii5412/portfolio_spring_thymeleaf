package com.portfolio.main.menu.dto.menu;

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
    private String createUserLoginId;

    public CreateMenu() {
    }

    public CreateMenu(String menuName, MenuType menuType, Long orderNum, String createUserLoginId) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.createUserLoginId = createUserLoginId;
    }

    public CreateMenu(Long upperId, String menuName, MenuType menuType, Long orderNum, String createUserLoginId) {
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.createUserLoginId = createUserLoginId;
    }


}
