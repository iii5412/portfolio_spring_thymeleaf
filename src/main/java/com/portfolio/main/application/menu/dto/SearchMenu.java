package com.portfolio.main.application.menu.dto;

import com.portfolio.main.common.util.page.PageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
public class SearchMenu extends PageDto {
    private Long id;
    private String menuName;
    private String roleCode;

    @Builder
    public SearchMenu(Long id, String menuName, String roleCode, Integer page, Integer size, List<String> sortFields, List<Sort.Direction> sort) {
        this.id = id;
        this.menuName = menuName;
        this.roleCode = roleCode;
        this.setPage(page);
        this.setSize(size);

        if (sortFields != null && !sortFields.isEmpty())
            this.setSortFields(sortFields);
        else
            this.setSortFields(List.of("updatedAt"));

        if (sort != null)
            this.setSorts(sort);
        else
            this.setSorts(List.of(Sort.Direction.ASC));
    }
}
