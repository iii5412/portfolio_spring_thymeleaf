package com.portfolio.main.menu.repository.custom.program;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.domain.QProgram;
import com.portfolio.main.menu.dto.program.DeleteProgram;
import com.portfolio.main.menu.dto.program.SearchProgram;
import com.portfolio.main.util.page.PageResult;
import com.portfolio.main.util.page.QuerydslUtils;
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
    public PageResult<Program> selectProgram(SearchProgram searchProgram, Pageable pageable) {
        final QProgram qProgram = QProgram.program;
        final BooleanBuilder whereBuilder = new BooleanBuilder();

        if (searchProgram.getId() != null)
            whereBuilder.and(qProgram.id.eq(searchProgram.getId()));

        if (StringUtils.hasText(searchProgram.getProgramName()))
            whereBuilder.and(qProgram.programName.contains(searchProgram.getProgramName()));


        if (StringUtils.hasText(searchProgram.getUrl()))
            whereBuilder.and(qProgram.url.contains(searchProgram.getUrl()));

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
    public void deleteProgram(DeleteProgram deleteProgram) {
        final QProgram qProgram = QProgram.program;
        final BooleanBuilder whereBuilder = new BooleanBuilder();

        if (deleteProgram.getId() == null) {

            if (StringUtils.hasText(deleteProgram.getProgramName()))
                whereBuilder.and(qProgram.programName.contains(deleteProgram.getProgramName()));

            if (StringUtils.hasText(deleteProgram.getProgramUrl()))
                whereBuilder.and(qProgram.url.contains(deleteProgram.getProgramUrl()));

        } else {
            whereBuilder.and(qProgram.id.eq(deleteProgram.getId()));
        }

        queryFactory
                .delete(qProgram)
                .where(whereBuilder)
                .execute();

        entityManager.clear();
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
