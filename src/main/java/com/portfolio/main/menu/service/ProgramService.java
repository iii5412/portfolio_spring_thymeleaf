package com.portfolio.main.menu.service;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.role.repository.RoleRepository;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.program.CreateProgram;
import com.portfolio.main.menu.dto.program.EditProgram;
import com.portfolio.main.menu.dto.program.SearchProgram;
import com.portfolio.main.menu.exception.DuplicatedProgramUrlException;
import com.portfolio.main.menu.exception.ProgramNotFoundException;
import com.portfolio.main.menu.repository.ProgramRepository;
import com.portfolio.main.util.page.PageResult;
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
public class ProgramService {
    private final ProgramRepository programRepository;
    private final MyUserDetailsService userDetailsService;

    public Program findById(Long id) throws ProgramNotFoundException {
        return programRepository.findById(id).orElseThrow(ProgramNotFoundException::new);
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public PageResult<Program> selectProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());
        return programRepository.selectProgram(searchProgram, pageable, true);
    }

    public PageResult<Program> selectManageProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());
        return programRepository.selectProgram(searchProgram, pageable, false);
    }

    public Long create(CreateProgram createProgram) {
        final User createUser = userDetailsService.findUserByLoginId(createProgram.getCreateUserLoginId());
        final Program program = new Program(createProgram.getProgramName(), createProgram.getUrl(), createUser);
        try {
            final Program savedProgram = programRepository.save(program);
            return savedProgram.getId();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedProgramUrlException();
        }

    }

    public void delete(Long programId) {
        programRepository.deleteProgram(programId);
    }

    public void edit(EditProgram editProgram) {
        final Program targetProgram = programRepository.findById(editProgram.getId()).orElseThrow(ProgramNotFoundException::new);
        final User modifier = userDetailsService.findUserByLoginId(editProgram.getEditUserLoginId());

        targetProgram.edit(editProgram, modifier);
        programRepository.save(targetProgram);
    }
}
