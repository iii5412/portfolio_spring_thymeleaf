package com.portfolio.main.menu.repository.custom;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.SearchProgram;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgramRepositoryImpl implements ProgramRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProgramRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Program> selectProgram(SearchProgram searchProgram) {
        return null;
    }
}
