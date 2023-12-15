package com.portfolio.main.menu.repository;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.domain.QProgram;
import com.portfolio.main.menu.repository.custom.program.ProgramRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramRepositoryCustom, QuerydslPredicateExecutor<Program>
        , QuerydslBinderCustomizer<QProgram> {

    @Override
    default void customize(QuerydslBindings bindings, QProgram root) {

    }
}
