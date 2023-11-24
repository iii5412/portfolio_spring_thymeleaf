package com.portfolio.main.account.user.repository.custom;

import com.portfolio.main.account.domain.QRole;
import com.portfolio.main.account.domain.QUser;
import com.portfolio.main.account.domain.QUserRole;
import com.portfolio.main.account.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        QUser qUser = QUser.user;
        QUserRole qUserRole = QUserRole.userRole;
        QRole qRole = QRole.role;

        final User findUser = queryFactory.selectFrom(qUser)
                .leftJoin(qUser.userRoles, qUserRole).fetchJoin()
                .leftJoin(qUserRole.role, qRole).fetchJoin()
                .where(qUser.loginId.eq(loginId))
                .fetchOne();

        return Optional.ofNullable(findUser);
    }

    @Override
    public List<User> findAll() {
        QUser qUser = QUser.user;
        QUserRole qUserRole = QUserRole.userRole;

        return queryFactory.selectFrom(qUser)
                .leftJoin(qUser.userRoles, qUserRole)
                .fetchJoin()
                .fetch();
    }
}
