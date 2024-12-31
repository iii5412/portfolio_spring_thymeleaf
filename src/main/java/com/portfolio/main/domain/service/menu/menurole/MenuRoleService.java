package com.portfolio.main.domain.service.menu.menurole;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menurole.dto.SaveMenuRole;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.domain.service.account.role.RoleService;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.repository.menu.MenuRoleRepository;
import com.portfolio.main.domain.service.menu.menu.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public MenuRole save(Long menuId, Long roleId) {
        final Menu menu = menuService.findById(menuId);
        final Role role = roleService.findById(roleId);
        final List<Role> flattenedAboveLevel = roleService.findFlattenedAboveLevel(role.getLevel());
        final MenuRole menuRole = new MenuRole(new MenuRole.MenuRoleId(menu.getId(), role.getId()), menu, role);

        List<MenuRole> savedMenuRoles = new ArrayList<>();

        flattenedAboveLevel
                .forEach(role -> {
                    
                });

        return menuRoleRepository.save(menuRole);
    }

    public void deleteByMenuId(Long menuId) {
        menuRoleRepository.deleteByMenuId(menuId);
    }

}
