package com.portfolio.main.application.menu.dto;

import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.presentation.rest.menu.request.EditMenuRequest;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;

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

    public boolean hasUpperId() {
        return this.upperId != null;
    }

    public boolean hasProgramId() {
        return this.programId != null;
    }
}
