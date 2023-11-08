package com.portfolio.main.menu.dto.program;

import lombok.*;

@Getter
@Setter
public class CreateProgram {
    private String programName;
    private String url;

    @Builder
    public CreateProgram(String programName, String url) {
        this.programName = programName;
        this.url = url;
    }
}
