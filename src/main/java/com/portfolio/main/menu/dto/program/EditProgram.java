package com.portfolio.main.menu.dto.program;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProgram {
    private Long id;
    private String programName;
    private String url;
    private String editUserLoginId;

    @Builder
    public EditProgram(Long id, String programName, String url, String editUserLoginId) {
        this.id = id;
        this.programName = programName;
        this.url = url;
        this.editUserLoginId = editUserLoginId;
    }
}
