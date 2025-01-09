package com.portfolio.main.domain.repository.menu;

import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.QMenu;
import com.portfolio.main.infrastructure.repository.custom.menu.MenuRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * 메뉴 엔터티에 대한 JpaRepository를 확장하는 인터페이스입니다.
 * 메뉴 선택, querydsl 조건자 바인딩 및 querydsl 바인딩 사용자 정의를 위한 사용자 정의 방법이 포함됩니다.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom, QuerydslPredicateExecutor<Menu>
        , QuerydslBinderCustomizer<QMenu> {

    @Override
    default void customize(QuerydslBindings bindings, QMenu root) {

    }
}
