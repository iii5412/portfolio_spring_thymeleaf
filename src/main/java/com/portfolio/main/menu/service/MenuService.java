package com.portfolio.main.menu.service;

import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.dto.menu.CreateMenu;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.menu.exception.MenuNotFoundException;
import com.portfolio.main.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu findById(Long id) throws MenuNotFoundException {
        return menuRepository.findById(id).orElseThrow(MenuNotFoundException::new);
    }

    public List<Menu> findAll() {
        final List<Menu> allMenus = menuRepository.findAll();
        return allMenus.stream().filter(menu -> menu.getUpperMenu() == null).collect(Collectors.toList());
    }

    public List<Menu> selectMenu(SearchMenu searchMenu) {
        final List<Menu> allMenus = menuRepository.findAll();
        final List<Menu> topMenus = allMenus.stream().filter(menu -> menu.getUpperMenu() == null).collect(Collectors.toList());
        return filterMenuByMenuName(topMenus, searchMenu.getMenuName());
    }

    public Long saveMenu(CreateMenu createMenu) {
        final Menu newMenu = new Menu(createMenu.getMenuName(), createMenu.getMenuType(), createMenu.getOrderNum(), createMenu.getLastModifiedBy());
        Menu savedMenu;
        if (createMenu.getUpperId() == null) {
            savedMenu = menuRepository.save(newMenu);
        } else {
            final Menu parentMenu = menuRepository.findById(createMenu.getUpperId()).orElseThrow(MenuNotFoundException::new);
            newMenu.setUpperMenu(parentMenu);
            savedMenu = menuRepository.save(newMenu);
        }
        return savedMenu.getId();
    }

    public void deleteMenu(Long menuId) throws CannotDeleteMenuWithSubmenusException {
        final Menu targetMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        if(targetMenu.getSubMenus().size() > 0){
            throw new CannotDeleteMenuWithSubmenusException();
        }

        menuRepository.deleteById(menuId);
    }

    private List<Menu> filterMenuByMenuName(List<Menu> allMenus, String searchMenuName) {
        List<Menu> filteredMenus = new ArrayList<>();
        for (Menu menu : allMenus) {

            if (menu.getMenuName().contains(searchMenuName))
                filteredMenus.add(menu);

            //TODO : menu.getSubMenus()시 DB 조회 발생 해결 필요.
            if (menu.getSubMenus().size() > 0) {
                //하위에 메뉴가 있으면 찾고자 하는 키워드를 갖고있는 menu가 있는지 재귀호출
                final List<Menu> filteredSubMenus = filterMenuByMenuName(menu.getSubMenus(), searchMenuName);
                //하위 메뉴중 필터링된 결과가 있으면
                if (filteredSubMenus.size() > 0) {
                    //현재 메뉴에 하위 메뉴로 change
                    menu.changeSubMenus(filteredSubMenus);
                    //현재 메뉴도 결과 컬렉션에 add
                    filteredMenus.add(menu);
                }
            }
        }

        return filteredMenus;
    }


}
