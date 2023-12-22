package com.portfolio.main.menu.service;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.role.dto.mapperdto.RoleMapperDto;
import com.portfolio.main.account.role.exception.NotFoundRoleException;
import com.portfolio.main.account.role.repository.RoleRepository;
import com.portfolio.main.account.role.service.RoleCode;
import com.portfolio.main.account.role.service.RoleService;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.domain.MenuRole;
import com.portfolio.main.menu.dto.menu.MenuDto;
import com.portfolio.main.menu.exception.NotFoundMenuRoleException;
import com.portfolio.main.menu.repository.MenuRoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MenuRoleService {
    private final MenuRoleRepository menuRoleRepository;
    private final RoleService roleService;

    public List<MenuRole> findByMenu(Menu menu) {
        return menuRoleRepository.findByMenuId(menu.getId());
    }

    public RoleCode findTopRoleCodeByMenu(Menu menu) {
        List<MenuRole> menuRoles = menuRoleRepository.findByMenuId(menu.getId());

        final Optional<RoleMapperDto> topRole = menuRoles.stream()
                .map(MenuRole::getRole)
                .map(role -> roleService.findByIdFlat(role.getId()))
                .min(Comparator.comparing(RoleMapperDto::getLevel));

        if (topRole.isPresent()) {
            return RoleCode.valueOf(topRole.get().getRoleCode());
        } else {
            throw new NotFoundMenuRoleException();
        }
    }

    /**
     * 메뉴별 최상위 권한을 찾아 MenuRole을 생성 및 반환한다.
     * @param List<Menu> menus
     * @return List<MenuRole>
     */
    public List<MenuRole> createTopMenuRolesForMenus(List<Menu> menus) {
        List<MenuRole> topMenuRoles = new ArrayList<>();

        for (Menu menu : menus) {
            List<MenuRole> menuRoles = menuRoleRepository.findByMenuId(menu.getId());

            menuRoles.stream()
                    .map(menuRole -> new AbstractMap.SimpleEntry<>(menuRole, roleService.findByIdFlat(menuRole.getRole().getId())))
                    .min(Comparator.comparing(entry -> entry.getValue().getLevel()))
                    .ifPresent(entry -> topMenuRoles.add(entry.getKey()));
        }

        return topMenuRoles;
    }


    public void changeRole(Menu menu, RoleCode roleCode) {
        final RoleMapperDto flatRole = roleService.findByRoleCodeFlat(roleCode);
        final List<RoleMapperDto> aboveLevelRoles = roleService.findFlattenedAboveLevel(flatRole.getLevel());

        menuRoleRepository.deleteByMenuId(menu.getId());

        aboveLevelRoles
                .forEach(roleMapperDto -> {
                    final Role role = roleService.findById(roleMapperDto.getId()).orElseThrow(NotFoundRoleException::new);
                    final MenuRole menuRole = new MenuRole(new MenuRole.MenuRoleId(menu.getId(), roleMapperDto.getId()), menu, role);
                    menuRoleRepository.save(menuRole);
                });
    }

    public List<MenuRole> save(MenuRole menuRole) {
        final Menu menu = menuRole.getMenu();
        final Role role = menuRole.getRole();
        final RoleMapperDto roleFlat = roleService.findByIdFlat(role.getId());
        final List<RoleMapperDto> roleAboveLevel = roleService.findFlattenedAboveLevel(roleFlat.getLevel());
        List<MenuRole> savedMenuRole = new ArrayList<>();

        //저장하고자 하는 권한의 레벨의 상위 권한들 까지 같이 저장한다.
        roleAboveLevel.stream()
                .map(r -> roleService.findById(r.getId()).orElseThrow(NotFoundRoleException::new))
                .forEach(r -> {
                    final MenuRole mr = new MenuRole(new MenuRole.MenuRoleId(menu.getId(), r.getId()), menu, r);
                    menuRoleRepository.save(mr);
                    savedMenuRole.add(mr);
                });
        return savedMenuRole;
    }

    public void deleteByMenuId(Long menuId) {
        menuRoleRepository.deleteByMenuId(menuId);
    }
}
