package com.portfolio.main.presentation.rest.program.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.domain.model.menu.Program;
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

    public ProgramResponse(ProgramDto programDto) {
        this.id = programDto.getId();
        this.programName = programDto.getProgramName();
        this.url = programDto.getUrl();
        this.createdAt = programDto.getCreatedAt();
        this.updatedAt = programDto.getUpdatedAt();
        this.lastUpdatedByUserLoginId = programDto.getLastUpdatedByUser().getLoginId();
    }
}
