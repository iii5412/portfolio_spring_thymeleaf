package com.portfolio.main.application.menu.dto;

import com.portfolio.main.application.menu.exception.InvalidCreateMenuException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 메뉴 생성
 */
@Getter
@Setter
public class CreateMenu {
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
    private MenuType menuType;
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

    /**
     * 메뉴에 상위 ID가 할당되어 있는지 확인.
     *
     * @return 메뉴에 상위 ID가 할당되어 있으면 true, 그렇지 않으면 false입니다.
     */
    public boolean hasUpperId() {
        return this.upperId != null;
    }

}
