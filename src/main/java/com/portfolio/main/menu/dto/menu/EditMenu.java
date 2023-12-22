package com.portfolio.main.menu.dto.menu;

import com.portfolio.main.account.role.service.RoleCode;
import com.portfolio.main.controller.api.menu.request.EditMenuRequest;
import com.portfolio.main.menu.service.MenuType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class EditMenu {
    private Long id;
    private Long upperId;
    private String menuName;
    private MenuType menuType;
    private Long orderNum;
    private Long programId;
    private String roleCode;
    private String editUserLoginId;

    public EditMenu(Long id, Long upperId, String menuName, MenuType menuType, Long orderNum, Long programId, RoleCode roleCode, String editUserLoginId) {
        this.id = id;
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.programId = programId;
        this.roleCode = roleCode.name();
        this.editUserLoginId = editUserLoginId;
    }

    public EditMenu(EditMenuRequest editMenuRequest, String editUserLoginId) {
        this.id = editMenuRequest.getId();
        this.upperId = editMenuRequest.getUpperId();
        this.menuName = editMenuRequest.getMenuName();
        this.menuType = editMenuRequest.getMenuType();
        this.orderNum = editMenuRequest.getOrderNum();
        this.programId = editMenuRequest.getProgramId();
        this.roleCode = editMenuRequest.getRoleCode();
        this.editUserLoginId = editUserLoginId;
    }
}
