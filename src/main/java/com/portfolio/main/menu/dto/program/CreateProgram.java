package com.portfolio.main.menu.dto.program;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateProgram {
    private String programName;
    private String url;
    private String createUserLoginId;

    public CreateProgram(String programName, String url) {
        this.programName = programName;
        this.url = url;
    }

    public CreateProgram(String programName, String url, String createUserLoginId) {
        this.programName = programName;
        this.url = url;
        this.createUserLoginId = createUserLoginId;
    }
}
