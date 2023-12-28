package com.portfolio.main.domain.service.menurole;

import com.portfolio.main.domain.service.role.RoleService;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.repository.menu.MenuRoleRepository;
import com.portfolio.main.domain.service.menu.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MenuRoleService {
    private final MenuService menuService;
    private final RoleService roleService;
    private final MenuRoleRepository menuRoleRepository;

    public List<MenuRole> findByMenuId(Long menuId) {
        return menuRoleRepository.findByMenuId(menuId);
    }

    public MenuRole save(MenuRole menuRole) {
        return menuRoleRepository.save(menuRole);
    }

    public void deleteByMenuId(Long menuId) {
        menuRoleRepository.deleteByMenuId(menuId);
    }

    public MenuRole createMenuRole(Long menuId, Long roleId) {
        final Menu menu = menuService.findById(menuId);
        final Role role = roleService.findById(roleId);

        return new MenuRole(new MenuRole.MenuRoleId(menuId, roleId), menu, role);
    }
}
