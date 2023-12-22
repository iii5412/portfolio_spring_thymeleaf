package com.portfolio.main.controller.api.menu.request;

import com.portfolio.main.account.role.service.RoleCode;
import com.portfolio.main.menu.service.MenuType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditMenuRequest {
    private Long id;
    private Long upperId;
    private String menuName;
    private MenuType menuType;
    private Long orderNum;
    private Long programId;
    private String roleCode;

    public EditMenuRequest(Long id, Long upperId, String menuName, MenuType menuType, Long orderNum, Long programId, RoleCode roleCode) {
        this.id = id;
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.programId = programId;
        this.roleCode = roleCode.name();
    }
}
