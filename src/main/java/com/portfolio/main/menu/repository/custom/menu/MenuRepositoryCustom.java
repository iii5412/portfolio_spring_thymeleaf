package com.portfolio.main.menu.repository.custom.menu;

import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.dto.menu.SearchMenu;

import java.util.List;

public interface MenuRepositoryCustom {
    List<Menu> selectMenuWithProgram();
}
