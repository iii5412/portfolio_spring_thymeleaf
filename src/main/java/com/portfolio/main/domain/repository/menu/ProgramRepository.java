package com.portfolio.main.domain.repository.menu;

import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.QProgram;
import com.portfolio.main.infrastructure.repository.custom.program.ProgramRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramRepositoryCustom, QuerydslPredicateExecutor<Program>
        , QuerydslBinderCustomizer<QProgram> {

    @Override
    default void customize(QuerydslBindings bindings, QProgram root) {

    }
}
