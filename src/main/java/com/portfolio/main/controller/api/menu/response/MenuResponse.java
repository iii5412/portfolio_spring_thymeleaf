package com.portfolio.main.controller.api.menu.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.menu.MenuDto;
import com.portfolio.main.menu.service.MenuType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MenuResponse {
    private Long id;

    private Long upperMenuId;

    private Long programId;

    private String menuName;

    private String programUrl;

    private MenuType menuType;

    private Long orderNum;

    private String lastModifiedByLoginId;

    private List<MenuResponse> subMenus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime updatedAt;

    public MenuResponse(MenuDto menuDto) {

        if (menuDto.hasUpperMenu())
            this.upperMenuId = menuDto.getUpperMenuId();

        this.id = menuDto.getId();
        this.menuName = menuDto.getMenuName();
        this.menuType = menuDto.getMenuType();
        this.orderNum = menuDto.getOrderNum();
        this.createdAt = menuDto.getCreatedAt();
        this.updatedAt = menuDto.getUpdatedAt();

        if (menuDto.hasSubMenus())
            this.subMenus = menuDto.getSubMenus().stream().map(MenuResponse::new).toList();

        if (menuDto.hasProgram()) {
            final Program program = menuDto.getProgram();
            this.programId = program.getId();
            this.programUrl = program.getUrl();
        }
    }

    public MenuResponse(Menu menu) {

        if (menu.hasUpperMenu())
            this.upperMenuId = menu.getUpperMenu().getId();

        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.menuType = menu.getMenuType();
        this.orderNum = menu.getOrderNum();
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getUpdatedAt();

        if (menu.hasSubMenus())
            this.subMenus = menu.getSubMenus().stream().map(MenuResponse::new).toList();

        if (menu.hasProgram()) {
            final Program program = menu.getProgram();
            this.programId = program.getId();
            this.programUrl = program.getUrl();
        }
    }
}
