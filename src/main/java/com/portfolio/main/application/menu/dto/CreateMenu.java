package com.portfolio.main.application.menu.dto;

import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMenu {
    private Long upperId;
    private String menuName;
    private MenuType menuType;
    private Long orderNum;
    private String roleCode;
    private String createUserLoginId;

    public CreateMenu() {
    }

    public CreateMenu(String menuName, MenuType menuType, Long orderNum, RoleCode roleCode, String createUserLoginId) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.roleCode = roleCode.name();
        this.createUserLoginId = createUserLoginId;
    }

    public CreateMenu(Long upperId, String menuName, MenuType menuType, Long orderNum, RoleCode roleCode, String createUserLoginId) {
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.roleCode = roleCode.name();
        this.createUserLoginId = createUserLoginId;
    }


}
