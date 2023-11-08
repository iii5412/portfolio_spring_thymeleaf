package com.portfolio.main.menu.repository.custom.program;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.program.DeleteProgram;
import com.portfolio.main.menu.dto.program.SearchProgram;
import com.portfolio.main.util.page.PageResult;
import org.springframework.data.domain.Pageable;

public interface ProgramRepositoryCustom {
    PageResult<Program> selectProgram(SearchProgram searchProgram, Pageable pageable);

    void deleteProgram(DeleteProgram deleteProgram);
}
