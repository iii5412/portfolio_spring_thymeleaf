package com.portfolio.main.application.menurole.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.service.MenuQueryService;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.menurole.dto.SaveMenuRole;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.service.RoleApplicationService;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.model.menu.exception.MenuRoleNotFoundException;
import com.portfolio.main.domain.service.account.role.RoleService;
import com.portfolio.main.domain.service.menu.menu.MenuService;
import com.portfolio.main.domain.service.menu.menurole.MenuRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MenuRoleApplicationService {
    private final MenuRoleService menuRoleService;
    private final MenuQueryService menuQueryService;
    private final RoleApplicationService roleApplicationService;
    private final MenuService menuService;
    private final RoleService roleService;

    public List<MenuRoleDto> findByMenu(MenuDto menuDto) {
        return menuRoleService.findByMenuId(menuDto.getId()).stream().map(MenuRoleDto::new).toList();
    }

    /**
     * 메뉴가 갖고있는 권한코드 중 제일 최상위 권한코드를 반환한다.
     *
     * @param menuId 메뉴 ID
     * @return RoleCode 최상위 RoleCode
     */
    public RoleCode findTopRoleCodeByMenu(Long menuId) {
        List<MenuRole> menuRoles = menuRoleService.findByMenuId(menuId);
        final Optional<RoleDto> topRole = menuRoles.stream()
                .map(MenuRole::getRole)
                .min(Comparator.comparing(Role::getLevel))
                .map(RoleDto::new);


        if (topRole.isPresent()) {
            return topRole.get().getRoleCode();
        } else {
            throw new MenuRoleNotFoundException();
        }
    }

    public void changeRole(MenuDto menuDto, RoleCode roleCode) {
        final Long menuId = menuDto.getId();
        final Menu menu = menuService.findById(menuId);
        final RoleDto flatRole = roleApplicationService.findByRoleCode(roleCode);
        final List<RoleDto> aboveLevelRoles = roleApplicationService.findFlattenedAboveLevel(flatRole.getLevel());

        menuRoleService.deleteByMenuId(menuId);

        aboveLevelRoles
                .forEach(roleDto -> {
                    final Role role = roleService.findById(roleDto.getId());
                    menuRoleService.save(menu, role);
                });
    }

    public List<MenuRoleDto> save(SaveMenuRole saveMenuRole) {
        final Long menuId = saveMenuRole.getMenuId();
        final Long roleId = saveMenuRole.getRoleId();
        final RoleDto roleFlat = roleApplicationService.findById(roleId);
        final List<RoleDto> roleAboveLevel = roleApplicationService.findFlattenedAboveLevel(roleFlat.getLevel());
        List<MenuRole> savedMenuRoles = new ArrayList<>();

        final Menu menu = menuService.findById(menuId);

        //저장하고자 하는 권한의 레벨의 상위 권한들 까지 같이 저장한다.
        roleAboveLevel
                .forEach(roleDto -> {
                    final Role role = roleService.findById(roleDto.getId());
                    final MenuRole savedMenuRole = menuRoleService.save(menu, role);
                    savedMenuRoles.add(savedMenuRole);
                });

        return savedMenuRoles.stream()
                .map(MenuRoleDto::new)
                .toList();
    }

    public void deleteByMenuId(Long menuId) {
        menuRoleService.deleteByMenuId(menuId);
    }
}
