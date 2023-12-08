package com.portfolio.main.menu.dto.program;

import com.portfolio.main.menu.exception.InvalidCreateProgramException;
import lombok.*;
import org.springframework.util.StringUtils;

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
        validate();
    }

    public CreateProgram(String programName, String url, String createUserLoginId) {
        this.programName = programName;
        this.url = url;
        this.createUserLoginId = createUserLoginId;
        validate();
    }

    public void validate() {
        final InvalidCreateProgramException invalidCreateProgramException = new InvalidCreateProgramException();

        if(!StringUtils.hasText(this.programName))
            invalidCreateProgramException.addValidation("programName", "입력해주세요.");

        if(!StringUtils.hasText(this.url))
            invalidCreateProgramException.addValidation("url", "입력해주세요.");

        if(invalidCreateProgramException.hasErrors())
            throw invalidCreateProgramException;

    }
}
