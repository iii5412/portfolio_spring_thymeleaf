package com.portfolio.main.util.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class PageResult<T> {
    private List<T> result;
    private Long totalCount;
    private int page;
    private int size;
    @Builder
    public PageResult(List<T> result, Long totalCount, int page, int size) {
        this.result = result;
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
    }
}
