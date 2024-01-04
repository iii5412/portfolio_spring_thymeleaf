package com.portfolio.main.presentation.rest.menu.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ManageMenuResponse {
    private Long id;

    private Long upperMenuId;

    private String upperMenuName;

    private Long programId;

    private String programName;

    private String menuName;

    private MenuType menuType;

    private Long orderNum;

    private String lastModifiedByLoginId;

    private List<ManageMenuResponse> subMenus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime updatedAt;

    public ManageMenuResponse(MenuDto menuDto) {

        if (menuDto.hasUpperMenu()) {
            this.upperMenuId = menuDto.getUpperMenu().getId();
            this.upperMenuName = menuDto.getUpperMenu().getMenuName();
        }

        this.id = menuDto.getId();
        this.menuName = menuDto.getMenuName();
        this.menuType = menuDto.getMenuType();
        this.orderNum = menuDto.getOrderNum();
        this.createdAt = menuDto.getCreatedAt();
        this.updatedAt = menuDto.getUpdatedAt();

        if (menuDto.hasSubMenus())
            this.subMenus = menuDto.getSubMenus().stream().map(ManageMenuResponse::new).toList();

        if (menuDto.hasProgram()) {
            final ProgramDto program = menuDto.getProgram();
            this.programId = program.getId();
            this.programName = program.getProgramName();
        }

        this.lastModifiedByLoginId = menuDto.getLastModifiedByUser().getLoginId();
    }
}
