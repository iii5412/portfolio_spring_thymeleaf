package com.portfolio.main.controller.api.program.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.main.menu.domain.Program;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProgramResponse {
    private Long id;
    private String programName;
    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "${locale.timezone}")
    private LocalDateTime updatedAt;

    private String lastUpdatedByUserLoginId;

    public ProgramResponse(Program program) {
        this.id = program.getId();
        this.programName = program.getProgramName();
        this.url = program.getUrl();
        this.createdAt = program.getCreatedAt();
        this.updatedAt = program.getUpdatedAt();
        this.lastUpdatedByUserLoginId = program.getLastUpdatedByUser().getLoginId();
    }
}
