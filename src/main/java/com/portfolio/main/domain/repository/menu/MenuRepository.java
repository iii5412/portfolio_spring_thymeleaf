package com.portfolio.main.domain.repository.menu;

import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.QMenu;
import com.portfolio.main.infrastructure.repository.custom.menu.MenuRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom, QuerydslPredicateExecutor<Menu>
        , QuerydslBinderCustomizer<QMenu> {

    @Override
    default void customize(QuerydslBindings bindings, QMenu root) {

    }
}
