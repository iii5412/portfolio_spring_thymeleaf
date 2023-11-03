package com.portfolio.main.menu.repository.custom;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.SearchProgram;

import java.util.List;

public interface ProgramRepositoryCustom {
    List<Program> selectProgram(SearchProgram searchProgram);
}
