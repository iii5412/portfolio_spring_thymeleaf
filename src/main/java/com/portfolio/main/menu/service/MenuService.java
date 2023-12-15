package com.portfolio.main.menu.service;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.menu.CreateMenu;
import com.portfolio.main.menu.dto.menu.EditMenu;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.menu.exception.MenuNotFoundException;
import com.portfolio.main.menu.exception.ProgramNotFoundException;
import com.portfolio.main.menu.exception.UpperMenuNotFoundException;
import com.portfolio.main.menu.repository.MenuRepository;
import com.portfolio.main.menu.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ProgramRepository programRepository;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public MenuService(MenuRepository menuRepository, ProgramRepository programRepository, MyUserDetailsService userDetailsService) {
        this.menuRepository = menuRepository;
        this.userDetailsService = userDetailsService;
        this.programRepository = programRepository;
    }

    public Menu findById(Long id) throws MenuNotFoundException {
        return menuRepository.findById(id).orElseThrow(MenuNotFoundException::new);
    }

    public List<Menu> findAllWithProgram() {
        final List<Menu> allMenus = menuRepository.selectMenuWithProgram();
        return allMenus.stream().filter(menu -> menu.getUpperMenu() == null).collect(Collectors.toList());
    }

    public List<Menu> selectMenu(SearchMenu searchMenu) {
        final List<Menu> allMenus = menuRepository.selectMenuWithProgram();
        final List<Menu> topMenus = allMenus.stream().filter(menu -> menu.getUpperMenu() == null).collect(Collectors.toList());
        return filterMenuByMenuName(topMenus, searchMenu.getMenuName());
    }

    public Long saveMenu(CreateMenu createMenu) throws UpperMenuNotFoundException {
        final User createUser = userDetailsService.findUserByLoginId(createMenu.getCreateUserLoginId());
        final Menu newMenu = new Menu(createMenu.getMenuName(), createMenu.getMenuType(), createMenu.getOrderNum(), createUser);

        Menu savedMenu;

        if (createMenu.getUpperId() != null) {
            final Menu upperMenu = menuRepository.findById(createMenu.getUpperId()).orElseThrow(UpperMenuNotFoundException::new);
            newMenu.setUpperMenu(upperMenu);
        }

        savedMenu = menuRepository.save(newMenu);
        return savedMenu.getId();
    }

    public void deleteMenu(Long menuId) throws MenuNotFoundException, CannotDeleteMenuWithSubmenusException {
        final Menu targetMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        if (!targetMenu.getSubMenus().isEmpty()) {
            throw new CannotDeleteMenuWithSubmenusException();
        }

        menuRepository.deleteById(menuId);
    }

    public void edit(Long menuId, EditMenu editMenu) {
        final Menu targetMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);

        Menu upperMenu = null;
        if(editMenu.getUpperId() != null) {
            upperMenu = menuRepository.findById(editMenu.getUpperId()).orElseThrow(MenuNotFoundException::new);
        }

        Program program = null;
        if(editMenu.getProgramId() != null) {
            program = programRepository.findById(editMenu.getProgramId()).orElseThrow(ProgramNotFoundException::new);
        }

        final User editUser = userDetailsService.findUserByLoginId(editMenu.getEditUserLoginId());


        targetMenu.edit(editMenu, upperMenu, program, editUser);
    }

    private List<Menu> filterMenuByMenuName(List<Menu> allMenus, String searchMenuName) {
        List<Menu> filteredMenus = new ArrayList<>();
        for (Menu menu : allMenus) {

            boolean menuAlreadyFiltered = false;

            if (searchMenuName == null)
                menuAlreadyFiltered = true;
            else if (menu.getMenuName().contains(searchMenuName))
                menuAlreadyFiltered = true;


            if(!menuAlreadyFiltered && menu.getSubMenus().size() > 0) {
                //하위에 메뉴가 있으면 찾고자 하는 키워드를 갖고있는 menu가 있는지 재귀호출
                final List<Menu> filteredSubMenus = filterMenuByMenuName(menu.getSubMenus(), searchMenuName);

                //하위 메뉴중 필터링된 결과가 있으면
                if (filteredSubMenus.size() > 0) {
                    menu.changeSubMenus(filteredSubMenus);
                    menuAlreadyFiltered = true;
                }
            }

            if(menuAlreadyFiltered)
                filteredMenus.add(menu);
        }

        return filteredMenus;
    }



}
