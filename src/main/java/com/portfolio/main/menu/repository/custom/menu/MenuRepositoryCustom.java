package com.portfolio.main.menu.repository.custom.menu;

import com.portfolio.main.account.role.service.RoleCode;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.util.page.PageResult;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuRepositoryCustom {
    List<Menu> selectMenu();
    List<Menu> selectMenuByRoleCode(RoleCode roleCode);
    PageResult<Menu> selectMenuWithPageable(SearchMenu searchMenu, Pageable pageable);

}
