package com.portfolio.main.application.program.dto;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.domain.model.menu.Program;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProgramDto {
    private Long id;
    private String programName;
    private String url;
    private boolean isImmutable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto lastUpdatedByUser;

    public ProgramDto(Program program) {
        this.id = program.getId();
        this.programName = program.getProgramName();
        this.url = program.getUrl();
        this.isImmutable = program.isImmutable();
        this.createdAt = program.getCreatedAt();
        this.updatedAt = program.getUpdatedAt();
        this.lastUpdatedByUser = new UserDto(program.getLastUpdatedByUser());
    }
}
