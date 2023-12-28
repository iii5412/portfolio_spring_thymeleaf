package com.portfolio.main.infrastructure.repository.custom.program;

import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.common.util.page.PageResult;
import org.springframework.data.domain.Pageable;

public interface ProgramRepositoryCustom {
    PageResult<Program> selectProgram(SearchProgram searchProgram, Pageable pageable, boolean existImmutable);

    void deleteProgram(Long programId);

    Program findByUrl(String url);
}
