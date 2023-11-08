package com.portfolio.main.menu.dto.program;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProgram {
    private Long id;
    private String programName;
    private String programUrl;

    @Builder
    public DeleteProgram(Long id, String programName, String programUrl) {
        this.id = id;
        this.programName = programName;
        this.programUrl = programUrl;
    }
}
