package com.portfolio.main.menu.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProgram {
    private String programName;
    private String url;

    @Builder
    public SearchProgram(String programName, String url) {
        this.programName = programName;
        this.url = url;
    }
}
