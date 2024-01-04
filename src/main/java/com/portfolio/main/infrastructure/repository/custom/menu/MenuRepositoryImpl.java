package com.portfolio.main.infrastructure.repository.custom.menu;

import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.common.util.page.QuerydslUtils;
import com.portfolio.main.domain.model.account.QRole;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.QMenu;
import com.portfolio.main.domain.model.menu.QMenuRole;
import com.portfolio.main.domain.model.menu.QProgram;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MenuRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Menu> selectMenu() {
        final JPAQuery<Menu> menuJPAQuery = menuJPAQuery();
        return menuJPAQuery.fetch();
    }

    @Override
    public List<Menu> selectMenuByRoleCode(RoleCode roleCode) {
        final QMenu qMenu = QMenu.menu;
        final QRole qRole = QRole.role;
        final QMenuRole qMenuRole = QMenuRole.menuRole;

        final JPAQuery<Menu> menuJPAQuery = menuJPAQuery();

        return menuJPAQuery
                .join(qMenuRole).on(qMenuRole.menu.eq(qMenu))
                .join(qMenuRole).on(qMenuRole.role.eq(qRole))
                .where(qRole.roleCode.eq(roleCode))
                .fetch();
    }


    @Override
    public PageResult<Menu> selectMenuWithPageable(SearchMenu searchMenu, Pageable pageable) {
        final QMenu qMenu = QMenu.menu;
        final QProgram qProgram = QProgram.program;
        final BooleanBuilder whereBuilder = new BooleanBuilder();

        if (StringUtils.hasText(searchMenu.getMenuName()))
            whereBuilder.and(qMenu.menuName.contains(searchMenu.getMenuName()));

        if (searchMenu.getId() != null)
            whereBuilder.and(qMenu.id.eq(searchMenu.getId()));

        final JPAQuery<Menu> query = queryFactory.selectFrom(qMenu)
                .leftJoin(qMenu.program, qProgram).fetchJoin()
                .where(whereBuilder)
                .orderBy(QuerydslUtils.getAllOrderSpecifiers(qMenu, pageable).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        final List<Menu> results = query.fetch();
        final Long totalCount = QuerydslUtils.fetchTotalCount(queryFactory, qMenu, whereBuilder);

        return PageResult.<Menu>builder()
                .result(results)
                .totalCount(totalCount)
                .build();

    }

    private JPAQuery<Menu> menuJPAQuery() {
        QMenu qMenu = QMenu.menu;
        QProgram qProgram = QProgram.program;
        final QMenuRole qMenuRole = QMenuRole.menuRole;

        return queryFactory
                .selectFrom(qMenu)
                .leftJoin(qMenu.program, qProgram)
                .leftJoin(qMenu.menuRoles, qMenuRole)
                .fetchJoin();
    }

}
