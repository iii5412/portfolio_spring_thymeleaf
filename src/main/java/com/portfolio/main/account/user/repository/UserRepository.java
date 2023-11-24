package com.portfolio.main.account.user.repository;

import com.portfolio.main.account.domain.QUser;
import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.user.repository.custom.UserRepositoryCustom;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.domain.QMenu;
import com.portfolio.main.menu.domain.QProgram;
import com.portfolio.main.menu.repository.custom.program.ProgramRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom, QuerydslPredicateExecutor<User>
, QuerydslBinderCustomizer<QUser>
{

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {

    }
}
