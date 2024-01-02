package com.portfolio.main.application.program.service;

import com.portfolio.main.application.login.service.UserQueryService;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.domain.service.menu.program.ProgramService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProgramManageService {
    private final ProgramService programService;

    public ProgramManageService(ProgramService programService) {
        this.programService = programService;
    }

    public Long create(CreateProgram createProgram) {
        return programService.createProgram(createProgram);
    }

    public void delete(Long programId) {
        programService.delete(programId);
    }

    public void edit(EditProgram editProgram) {
        programService.edit(editProgram);
    }
}
