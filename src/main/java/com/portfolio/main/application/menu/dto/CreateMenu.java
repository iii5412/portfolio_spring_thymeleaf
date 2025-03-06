package com.portfolio.main.application.menu.dto;

import com.portfolio.main.application.menu.exception.InvalidCreateMenuException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 메뉴 생성
 */
@Getter
@Setter
@NoArgsConstructor
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
    private RoleCode roleCode;
    /**
     * 메뉴를 생성한 사용자의 로그인 ID
     */
    private String createUserLoginId;

    public CreateMenu(String menuName, MenuType menuType, Long orderNum, RoleCode roleCode, String createUserLoginId) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.roleCode = roleCode;
        this.createUserLoginId = createUserLoginId;
    }

    public CreateMenu(Long upperId, String menuName, MenuType menuType, Long orderNum, RoleCode roleCode, String createUserLoginId) {
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.roleCode = roleCode;
        this.createUserLoginId = createUserLoginId;
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
