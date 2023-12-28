package com.portfolio.main.domain.repository.account;


import com.portfolio.main.domain.model.account.QUser;
import com.portfolio.main.infrastructure.repository.custom.user.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<com.portfolio.main.domain.model.account.User, Long>, UserRepositoryCustom, QuerydslPredicateExecutor<com.portfolio.main.domain.model.account.User>
        , QuerydslBinderCustomizer<QUser> {

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {

    }
}
