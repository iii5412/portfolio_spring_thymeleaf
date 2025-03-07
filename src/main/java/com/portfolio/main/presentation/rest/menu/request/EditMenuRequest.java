package com.portfolio.main.presentation.rest.menu.request;

import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.type.MenuType;
import com.portfolio.main.presentation.rest.menu.exception.InvalidMenuInputException;
import com.portfolio.main.presentation.rest.menu.exception.MissingMenuIdException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class EditMenuRequest {
    private Long id;
    private Long upperId;
    private String menuName;
    private String menuType;
    private Long orderNum;
    private Long programId;
    private String roleCode;

    public EditMenuRequest(Long id, Long upperId, String menuName, MenuType menuType, Long orderNum, Long programId, RoleCode roleCode) {
        this.id = id;
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType.name();
        this.orderNum = orderNum;
        this.programId = programId;
        this.roleCode = roleCode.name();
    }

    public void validate() {
        final InvalidMenuInputException invalidMenuInputException = new InvalidMenuInputException();

        if (this.id == null)
            throw new MissingMenuIdException();

        if (!StringUtils.hasText(menuName))
            invalidMenuInputException.addValidation("menuName", "입력해주세요.");

        if (!StringUtils.hasText(menuType))
            invalidMenuInputException.addValidation("menuType", "입력해주세요.");

        if (orderNum == null)
            invalidMenuInputException.addValidation("orderNum", "입력해주세요.");

        if (menuType.equals(MenuType.PROGRAM.name()) && programId == null)
            invalidMenuInputException.addValidation("programId", "입력해주세요.");

        if (!StringUtils.hasText(roleCode))
            invalidMenuInputException.addValidation("roleCode", "입력해주세요.");

        if (invalidMenuInputException.hasErrors())
            throw invalidMenuInputException;
    }
}
