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

    @Builder
    public PageResult(List<T> result, Long totalCount) {
        this.result = result;
        this.totalCount = totalCount;
    }
}
