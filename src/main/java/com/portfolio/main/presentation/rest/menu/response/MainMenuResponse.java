package com.portfolio.main.presentation.rest.menu.response;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainMenuResponse {
    private Long id;

    private Long upperMenuId;

    private String menuName;

    private String programUrl;

    private MenuType menuType;

    private Long orderNum;

    private List<MainMenuResponse> subMenus;

    public MainMenuResponse(MenuDto menuDto) {

        if (menuDto.hasUpperMenu()) {
            this.upperMenuId = menuDto.getUpperMenuId();
        }

        this.id = menuDto.getId();
        this.menuName = menuDto.getMenuName();
        this.menuType = menuDto.getMenuType();
        this.orderNum = menuDto.getOrderNum();

        if (menuDto.hasSubMenus())
            this.subMenus = menuDto.getSubMenus().stream().map(MainMenuResponse::new).toList();

        if (menuDto.hasProgram()) {
            final ProgramDto program = menuDto.getProgram();
            this.programUrl = program.getUrl();
        }
    }
}
