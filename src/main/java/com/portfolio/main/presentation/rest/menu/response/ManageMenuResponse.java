package com.portfolio.main.presentation.rest.menu.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.dto.MenuManageDto;
import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.type.MenuType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ManageMenuResponse {
    private Long id;

    private Long upperMenuId;

    private String upperMenuName;

    private Long programId;

    private String roleCode;

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

    public ManageMenuResponse(MenuManageDto menuManageDto) {

        if (menuManageDto.hasUpperMenu()) {
            this.upperMenuId = menuManageDto.getUpperMenu().getId();
            this.upperMenuName = menuManageDto.getUpperMenu().getMenuName();
        }

        this.id = menuManageDto.getId();
        this.menuName = menuManageDto.getMenuName();
        this.menuType = menuManageDto.getMenuType();
        this.roleCode = menuManageDto.getRoleCode().name();
        this.orderNum = menuManageDto.getOrderNum();
        this.createdAt = menuManageDto.getCreatedAt();
        this.updatedAt = menuManageDto.getUpdatedAt();

        if (menuManageDto.hasSubMenus())
            this.subMenus = menuManageDto.getSubMenus().stream()
                    .map(ManageMenuResponse::new).toList();

        if (menuManageDto.hasProgram()) {
            final ProgramDto program = menuManageDto.getProgram();
            this.programId = program.getId();
            this.programName = program.getProgramName();
        }

        this.lastModifiedByLoginId = menuManageDto.getLastModifiedByUser().getLoginId();
    }

}
