package com.portfolio.main.menu.reqeust;

import com.portfolio.main.menu.dto.program.SearchProgram;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProgramRequest {
    private String programName;
    private String url;

    public SearchProgram toSearchProgram() {
        return SearchProgram.builder()
                .programName(this.programName)
                .url(this.url)
                .build();
    }
}
