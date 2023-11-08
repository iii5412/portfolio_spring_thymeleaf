package com.portfolio.main.menu.service;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.program.DeleteProgram;
import com.portfolio.main.menu.dto.program.CreateProgram;
import com.portfolio.main.menu.dto.program.SearchProgram;
import com.portfolio.main.menu.exception.ProgramNotFoundException;
import com.portfolio.main.menu.repository.ProgramRepository;
import com.portfolio.main.util.page.PageResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProgramService {
    private final ProgramRepository programRepository;

    public Program findById(Long id) throws ProgramNotFoundException {
        return programRepository.findById(id).orElseThrow(ProgramNotFoundException::new);
    }

    public PageResult<Program> selectProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());
        return programRepository.selectProgram(searchProgram, pageable);
    }

    public Long save(CreateProgram createProgram) {
        final Program program = new Program(createProgram.getProgramName(), createProgram.getUrl());

        final Program savedProgram = programRepository.save(program);
        return savedProgram.getId();
    }

    public void delete(DeleteProgram deleteProgram) {
        programRepository.deleteProgram(deleteProgram);
    }
}
