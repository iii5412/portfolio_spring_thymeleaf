package com.portfolio.main.infrastructure.repository.custom.program;

import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.common.util.page.QuerydslUtils;
import com.portfolio.main.domain.model.menu.QProgram;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class ProgramRepositoryImpl implements ProgramRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public ProgramRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public PageResult<Program> selectProgram(SearchProgram searchProgram, Pageable pageable, boolean existImmutable) {
        final QProgram qProgram = QProgram.program;
        final BooleanBuilder whereBuilder = new BooleanBuilder();

        if (searchProgram.getId() != null)
            whereBuilder.and(qProgram.id.eq(searchProgram.getId()));

        if (StringUtils.hasText(searchProgram.getProgramName()))
            whereBuilder.and(qProgram.programName.contains(searchProgram.getProgramName()));

        if (StringUtils.hasText(searchProgram.getUrl()))
            whereBuilder.and(qProgram.url.contains(searchProgram.getUrl()));

        if(!existImmutable)
            whereBuilder.and(qProgram.isImmutable.eq(false));

        final JPAQuery<Program> query = queryFactory
                .selectFrom(qProgram)
                .where(whereBuilder)
                .orderBy(QuerydslUtils.getAllOrderSpecifiers(qProgram, pageable).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        final List<Program> results = query.fetch();
        final Long total = QuerydslUtils.fetchTotalCount(queryFactory, qProgram, whereBuilder);

        return PageResult.<Program>builder().result(results).totalCount(total).build();
    }

    @Override
    public void deleteProgram(Long programId) {
        final QProgram qProgram = QProgram.program;

        queryFactory
                .delete(qProgram)
                .where(qProgram.id.eq(programId))
                .execute();

        entityManager.clear();
    }

    @Override
    public Program findByUrl(String url) {
        final QProgram qProgram = QProgram.program;

        return queryFactory
                .selectFrom(qProgram)
                .where(qProgram.url.eq(url))
                .fetchOne();
    }

//    private <T> List<OrderSpecifier> getAllOrderSpecifiers(EntityPathBase<T> entityPath, Pageable pageable) {
//        PathBuilder<T> entityPathBuilder = new PathBuilder<>(entityPath.getType(), entityPath.getMetadata());
//        List<OrderSpecifier> orders = new ArrayList<>();
//
//        if (!pageable.getSort().isEmpty()) {
//            for (Sort.Order order : pageable.getSort()) {
//                Path<Object> path = entityPathBuilder.get(order.getProperty());
//                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
//                orders.add(new OrderSpecifier(direction, path));
//            }
//        }
//
//        return orders;
//    }
}
