package com.portfolio.main.menu.dto.mapperdto;

import com.portfolio.main.menu.service.MenuType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MenuMapperDto {
    private Long id;
    private Long upperId;
    private String menuName;
    private Long orderNum;
    private MenuType menuType;
    private Long programId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long lastModifiedBy;
    private Integer level;

    @Override
    public String toString() {
        return "MenuMapperDto{" +
                "id=" + id +
                ", upperId=" + upperId +
                ", menuName='" + menuName + '\'' +
                ", order_num=" + orderNum +
                ", menuType=" + menuType +
                ", programId=" + programId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastModifiedBy=" + lastModifiedBy +
                ", level=" + level +
                '}';
    }

}
