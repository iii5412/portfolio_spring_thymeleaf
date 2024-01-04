package com.portfolio.main.application.menu.dto;

import com.portfolio.main.application.menu.exception.InvalidCreateMenuException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

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

    public void validate() {
        final InvalidCreateMenuException invalidCreateMenuException = new InvalidCreateMenuException();

        if(!StringUtils.hasText(this.menuName))
            invalidCreateMenuException.addValidation("menuName", "입력해주세요.");

        if(this.menuType == null)
            invalidCreateMenuException.addValidation("menuType", "입력해주세요.");

        if(this.orderNum == null)
            invalidCreateMenuException.addValidation("orderNum", "입력해주세요.");

        if(StringUtils.hasText(this.roleCode))
            invalidCreateMenuException.addValidation("roleCode", "입력해주세요.");

        if(invalidCreateMenuException.hasErrors())
            throw invalidCreateMenuException;
    }

    public boolean hasUpperId() {
        return this.upperId != null;
    }

}
