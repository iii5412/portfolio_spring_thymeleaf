package com.portfolio.main.controller.api.menu.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.Program;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public MenuResponse(Menu menu) {

        if (menu.hasUpperMenu())
            this.upperMenuId = menu.getUpperMenu().getId();

        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.menuType = menu.getMenuType();
        this.orderNum = menu.getOrderNum();
        this.createAt = menu.getCreateAt();
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
