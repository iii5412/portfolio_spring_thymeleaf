package com.portfolio.main.application.program.service;

import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.application.program.exception.DuplicatedProgramUrlException;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.exception.ProgramNotFoundException;
import com.portfolio.main.domain.service.program.ProgramService;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProgramApplicationService {
    private final ProgramService programService;
    private final MyUserDetailsService userDetailsService;

    public Program findById(Long id) throws ProgramNotFoundException {
        return programService.findById(id);
    }

    public List<Program> findAll() {
        return programService.findAll();
    }

    public PageResult<Program> selectProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());
        return programService.selectProgram(searchProgram, pageable, true);
    }

    public PageResult<Program> selectManageProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());
        return programService.selectProgram(searchProgram, pageable, false);
    }

    public Long create(CreateProgram createProgram) {
        final User createUser = userDetailsService.findUserByLoginId(createProgram.getCreateUserLoginId());
        final Program program = new Program(createProgram.getProgramName(), createProgram.getUrl(), createUser);
        try {
            final Program savedProgram = programService.save(program);
            return savedProgram.getId();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedProgramUrlException();
        }

    }

    public void delete(Long programId) {
        programService.delete(programId);
    }

    public void edit(EditProgram editProgram) {
        final Program targetProgram = programService.findById(editProgram.getId());
        final User modifier = userDetailsService.findUserByLoginId(editProgram.getEditUserLoginId());

        targetProgram.edit(editProgram, modifier);
        programService.save(targetProgram);
    }
}
