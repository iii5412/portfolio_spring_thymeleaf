package com.portfolio.main.presentation.rest.menu.request;

import com.portfolio.main.application.menu.dto.CreateMenu;
import com.portfolio.main.application.menu.exception.InvalidCreateMenuException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class CreateMenuRequest {
    /**
     * 메뉴 상위 ID.
     */
    private Long upperId;
    /**
     * 메뉴명
     */
    private String menuName;
    /**
     * FOLDER 또는 PROGRAM일 수 있는 메뉴 유형을 나타냅니다.
     */
    private String menuType;
    /**
     * 정렬 순서
     */
    private Long orderNum;
    /**
     * 메뉴 접근 권한 RoleCode String Value
     */
    private String roleCode;
    /**
     * 메뉴를 생성한 사용자의 로그인 ID
     */
    private String createUserLoginId;

    public CreateMenuRequest() {
    }

    public CreateMenuRequest(String menuName, String menuType, Long orderNum, RoleCode roleCode, String createUserLoginId) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.roleCode = roleCode.name();
        this.createUserLoginId = createUserLoginId;
    }

    public CreateMenuRequest(Long upperId, String menuName, String menuType, Long orderNum, RoleCode roleCode, String createUserLoginId) {
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.roleCode = roleCode.name();
        this.createUserLoginId = createUserLoginId;
    }

    public CreateMenu toCreateMenu() {
        return new CreateMenu(this.upperId, this.menuName, MenuType.valueOf(this.menuType), this.orderNum, RoleCode.valueOf(this.roleCode), this.createUserLoginId);
    }

    public void validate() {
        final InvalidCreateMenuException invalidCreateMenuException = new InvalidCreateMenuException();

        if (!StringUtils.hasText(this.menuName))
            invalidCreateMenuException.addValidation("menuName", "입력해주세요.");

        if (!StringUtils.hasText(this.menuType))
            invalidCreateMenuException.addValidation("menuType", "입력해주세요.");

        if (this.orderNum == null)
            invalidCreateMenuException.addValidation("orderNum", "입력해주세요.");

        if (!StringUtils.hasText(this.roleCode))
            invalidCreateMenuException.addValidation("roleCode", "입력해주세요.");

        if (invalidCreateMenuException.hasErrors())
            throw invalidCreateMenuException;
    }
}
