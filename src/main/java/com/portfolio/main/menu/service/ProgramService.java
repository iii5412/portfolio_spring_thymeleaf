package com.portfolio.main.menu.service;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.SearchProgram;
import com.portfolio.main.menu.exception.ProgramNotFoundException;
import com.portfolio.main.menu.repository.ProgramRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProgramService {
    private final ProgramRepository repository;

    public Program findById(Long id) throws ProgramNotFoundException {
        return repository.findById(id).orElseThrow(ProgramNotFoundException::new);
    }

    public List<Program> selectProgram(SearchProgram searchProgram) {
        return repository.selectProgram(searchProgram);
    }

}
