package com.portfolio.main.domain.service.program;

import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.exception.ProgramNotFoundException;
import com.portfolio.main.domain.repository.menu.ProgramRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;

    public Program findById(Long id) throws ProgramNotFoundException {
        return programRepository.findById(id).orElseThrow(ProgramNotFoundException::new);
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public PageResult<Program> selectProgram(SearchProgram searchProgram, PageRequest pageRequest, boolean existImmutable) {
        return programRepository.selectProgram(searchProgram, pageRequest, existImmutable);
    }

    public Program save(Program program) {
        return programRepository.save(program);
    }

    public void delete(Long id) {
        programRepository.deleteProgram(id);
    }
}
