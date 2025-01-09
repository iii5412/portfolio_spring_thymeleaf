package com.portfolio.main.domain.repository.account;


import com.portfolio.main.domain.model.account.QUser;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.infrastructure.repository.custom.user.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * 사용자 엔터티에 대한 저장소 기능을 제공하는 인터페이스
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom, QuerydslPredicateExecutor<User>
        , QuerydslBinderCustomizer<QUser> {

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {

    }
}
