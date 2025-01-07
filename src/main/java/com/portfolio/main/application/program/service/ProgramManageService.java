package com.portfolio.main.application.program.service;

import com.portfolio.main.application.login.service.UserQueryService;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.application.program.exception.DuplicatedProgramUrlException;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.service.menu.program.ProgramService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class ProgramManageService {
    private final ProgramService programService;

    public ProgramManageService(ProgramService programService) {
        this.programService = programService;
    }

    public Long create(CreateProgram createProgram) {

        if(isUrlPresent(createProgram.getUrl()))
            throw new DuplicatedProgramUrlException();

        return programService.createProgram(createProgram);
    }

    public void delete(Long programId) {
        programService.delete(programId);
    }

    public void edit(EditProgram editProgram) {
        programService.edit(editProgram);
    }

    public boolean isUrlPresent(String url) {

        if(!StringUtils.hasText(url))
            return false;

        Program program = programService.findByUrl(url);
        return program != null;
    }
}
