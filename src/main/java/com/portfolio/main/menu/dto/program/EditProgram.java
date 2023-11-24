package com.portfolio.main.menu.dto.program;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProgram {
    private String programName;
    private String url;
    private String editUserLoginId;
    private String roleCode;

    @Builder
    public EditProgram(String programName, String url, String editUserLoginId, String roleCode) {
        this.programName = programName;
        this.url = url;
        this.editUserLoginId = editUserLoginId;
        this.roleCode = roleCode;
    }
}
