package com.portfolio.main.presentation.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IdRequest {
    private Long id;

    public IdRequest(Long id) {
        this.id = id;
    }
}
