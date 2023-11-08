package com.portfolio.main.menu.dto.menu;

import com.portfolio.main.common.PageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMenu extends PageDto {
    private Long id;
    private Long upperId;
    private String menuName;
    private Long orderNum;

    @Builder
    public SearchMenu(Long id, Long upperId, String menuName, Long orderNum) {
        this.id = id;
        this.upperId = upperId;
        this.menuName = menuName;
        this.orderNum = orderNum;
    }
}
