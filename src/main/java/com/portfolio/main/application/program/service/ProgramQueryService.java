package com.portfolio.main.application.program.service;

import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.exception.ProgramNotFoundException;
import com.portfolio.main.domain.service.menu.program.ProgramService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProgramQueryService {
    private final ProgramService programService;

    public ProgramDto findById(Long id) throws ProgramNotFoundException {
        final Program program = programService.findById(id);
        return new ProgramDto(program);
    }

    public List<ProgramDto> findAll() {
        return programService.findAll().stream().map(ProgramDto::new).toList();
    }

    public PageResult<ProgramDto> selectProgramWithoutDefaultProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());

        final PageResult<Program> programPageResult = programService.selectProgram(searchProgram, pageable, true);

        return new PageResult<>(
                programPageResult.getResult().stream().map(ProgramDto::new).toList(),
                programPageResult.getTotalCount(),
                programPageResult.getPage(),
                programPageResult.getSize()
        );
    }

    public PageResult<ProgramDto> selectManageProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());
        final PageResult<Program> programPageResult = programService.selectProgram(searchProgram, pageable, false);

        return new PageResult<>(
                programPageResult.getResult().stream().map(ProgramDto::new).toList(),
                programPageResult.getTotalCount(),
                programPageResult.getPage(),
                programPageResult.getSize()
        );
    }


}
