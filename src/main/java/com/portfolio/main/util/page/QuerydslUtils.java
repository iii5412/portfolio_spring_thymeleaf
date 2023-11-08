package com.portfolio.main.util.page;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuerydslUtils {
    public static <T> List<OrderSpecifier> getAllOrderSpecifiers(EntityPathBase<T> entityPath, Pageable pageable) {
        PathBuilder<T> entityPathBuilder = new PathBuilder<>(entityPath.getType(), entityPath.getMetadata());
        List<OrderSpecifier> orders = new ArrayList<>();

        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Path<Object> path = entityPathBuilder.get(order.getProperty());
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                orders.add(new OrderSpecifier(direction, path));
            }
        }

        return orders;
    }

    public static Long fetchTotalCount(JPAQueryFactory queryFactory, EntityPathBase<?> entity, Predicate predicate) {
        return queryFactory
                .select(entity.count())
                .from(entity)
                .where(predicate)
                .fetchOne();
    }


}
