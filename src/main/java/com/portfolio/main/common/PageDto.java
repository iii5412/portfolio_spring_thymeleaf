package com.portfolio.main.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class PageDto {
    private Integer page;
    private Integer size;
    private List<String> sortFields;
    private List<Sort.Direction> sorts;

    public void setPage(Integer page) {
        this.page = (page == null || page < 1) ? 1 : page;
    }

    public void setSize(Integer size) {
        this.size = (size == null || size < 1) ? 10 : size;
    }
    // 정렬 정보를 기반으로 Sort 객체를 생성하는 메소드
    public Sort getSort() {
        if (sortFields == null || sortFields.isEmpty()) {
            return Sort.unsorted(); // 정렬 필드가 없는 경우
        }

        List<Sort.Order> orders = IntStream.range(0, sortFields.size())
                .mapToObj(i -> {
                    String sortField = sortFields.get(i);
                    Sort.Direction sortDirection = (sorts != null && i < sorts.size()) ? sorts.get(i) : Sort.Direction.ASC;
                    return new Sort.Order(sortDirection, sortField);
                })
                .collect(Collectors.toList());

        return Sort.by(orders);
    }
}
