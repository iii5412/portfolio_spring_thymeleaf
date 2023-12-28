package com.portfolio.main.domain.service.menu;

import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.exception.MenuNotFoundException;
import com.portfolio.main.domain.repository.menu.MenuRepository;
import com.portfolio.main.infrastructure.repository.mapper.menu.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Autowired
    public MenuService(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    public Menu findById(Long id) throws MenuNotFoundException {
        return menuRepository.findById(id).orElseThrow(MenuNotFoundException::new);
    }

    public List<Menu> selectMenuByRoleCode(RoleCode roleCode) {
        return menuRepository.selectMenuByRoleCode(roleCode);
    }

    public PageResult<Menu> selectMenuWithPageable(SearchMenu searchMenu, PageRequest pageRequest) {
        return menuRepository.selectMenuWithPageable(searchMenu, pageRequest);
    }

    public List<Menu> selectMenu() {
        return menuRepository.selectMenu();
    }

    public Menu save(Menu saveMenu) {
        return menuRepository.save(saveMenu);
    }

    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

}
