package com.portfolio.main.menu.repository.custom.menu;

import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.QMenu;
import com.portfolio.main.menu.domain.QProgram;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public MenuRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public List<Menu> selectMenuWithProgram() {
        QMenu qMenu = QMenu.menu;
        QProgram qProgram = QProgram.program;

        final List<Menu> result = queryFactory.selectFrom(qMenu)
                .leftJoin(qMenu.program, qProgram).fetchJoin()
//                .where(createWhereBuilder(searchMenu))
                .fetch();
        return result;
    }

    private BooleanBuilder createWhereBuilder(SearchMenu searchMenu) {
        final BooleanBuilder whereBuilder = new BooleanBuilder();
        QMenu qMenu = QMenu.menu;

        if(searchMenu.getId() != null)
            whereBuilder.and(qMenu.id.eq(searchMenu.getId()));

        if(StringUtils.hasText(searchMenu.getMenuName()))
            whereBuilder.and(qMenu.menuName.contains(searchMenu.getMenuName()));

        return whereBuilder;
    }

}
