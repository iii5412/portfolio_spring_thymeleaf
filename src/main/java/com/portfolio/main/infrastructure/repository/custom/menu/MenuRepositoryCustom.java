package com.portfolio.main.infrastructure.repository.custom.menu;

import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.common.util.page.PageResult;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuRepositoryCustom {
    List<Menu> selectMenu();
    List<Menu> selectMenuByRoleCode(RoleCode roleCode);
    PageResult<Menu> selectMenuWithPageable(SearchMenu searchMenu, Pageable pageable);

}
