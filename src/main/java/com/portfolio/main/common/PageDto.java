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
    private int page;
    private int size;
    private List<String> sortFields;
    private List<Sort.Direction> sorts;

    public void setPage(int page) {
        if(page == 0)
            this.page = 1;
        else
            this.page = page;
    }

    public void setSize(int size) {
        if(size == 0)
            this.size = 10;
        else
            this.size = size;
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
