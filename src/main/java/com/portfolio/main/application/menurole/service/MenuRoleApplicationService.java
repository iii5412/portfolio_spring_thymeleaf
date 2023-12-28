package com.portfolio.main.application.menurole.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.menurole.dto.SaveMenuRole;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.Menu;
import com.portfolio.main.domain.model.menu.MenuRole;
import com.portfolio.main.domain.model.menu.exception.MenuRoleNotFoundException;
import com.portfolio.main.domain.service.menurole.MenuRoleService;
import com.portfolio.main.domain.service.role.RoleService;
import com.portfolio.main.infrastructure.repository.mapper.role.dto.RoleMapperDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MenuRoleApplicationService {
    private final MenuRoleService menuRoleService;
    private final RoleService roleService;

    public List<MenuRole> findByMenu(Menu menu) {
        return menuRoleService.findByMenuId(menu.getId());
    }

    /**
     * 메뉴가 갖고있는 권한코드 중 제일 최상위 권한코드를 반환한다.
     * @param menuId 메뉴 ID
     * @return RoleCode 최상위 RoleCode
     */
    public RoleCode findTopRoleCodeByMenu(Long menuId) {
        List<MenuRole> menuRoles = menuRoleService.findByMenuId(menuId);

        final Optional<RoleMapperDto> topRole = menuRoles.stream()
                .map(MenuRole::getRole)
                .map(role -> roleService.findByIdFlat(role.getId()))
                .min(Comparator.comparing(RoleMapperDto::getLevel));

        if (topRole.isPresent()) {
            return RoleCode.valueOf(topRole.get().getRoleCode());
        } else {
            throw new MenuRoleNotFoundException();
        }
    }

    /**
     * 메뉴별 최상위 권한을 찾아 MenuRole을 생성 및 반환한다.
     * @param menuIds
     * @return List<MenuRole>
     */
    public List<MenuRole> createTopMenuRolesForMenus(List<Long> menuIds) {
        List<MenuRole> topMenuRoles = new ArrayList<>();

        for (Long menuId : menuIds) {
            List<MenuRole> menuRoles = menuRoleService.findByMenuId(menuId);

            menuRoles.stream()
                    .map(menuRole -> new AbstractMap.SimpleEntry<>(menuRole, roleService.findByIdFlat(menuRole.getRole().getId())))
                    .min(Comparator.comparing(entry -> entry.getValue().getLevel()))
                    .ifPresent(entry -> topMenuRoles.add(entry.getKey()));
        }

        return topMenuRoles;
    }


    public void changeRole(MenuDto menuDto, RoleCode roleCode) {
        final Long menuId = menuDto.getId();
        final RoleMapperDto flatRole = roleService.findByRoleCodeFlat(roleCode);
        final List<RoleMapperDto> aboveLevelRoles = roleService.findFlattenedAboveLevel(flatRole.getLevel());

        menuRoleService.deleteByMenuId(menuId);

        aboveLevelRoles
                .forEach(roleMapperDto -> {
                    final MenuRole menuRole = menuRoleService.createMenuRole(menuId, roleMapperDto.getId());
                    menuRoleService.save(menuRole);
                });
    }

    public List<MenuRoleDto> save(SaveMenuRole saveMenuRole) {
        final Long menuId = saveMenuRole.getMenuId();
        final Long roleId = saveMenuRole.getRoleId();
        final RoleMapperDto roleFlat = roleService.findByIdFlat(roleId);
        final List<RoleMapperDto> roleAboveLevel = roleService.findFlattenedAboveLevel(roleFlat.getLevel());
        List<MenuRole> savedMenuRole = new ArrayList<>();

        //저장하고자 하는 권한의 레벨의 상위 권한들 까지 같이 저장한다.
        roleAboveLevel.stream()
                .map(r -> roleService.findById(r.getId()))
                .forEach(r -> {
                    final MenuRole mr = menuRoleService.createMenuRole(menuId, roleId);
                    menuRoleService.save(mr);
                    savedMenuRole.add(mr);
                });

        return savedMenuRole.stream()
                .map(MenuRoleDto::new)
                .toList();
    }

    public void deleteByMenuId(Long menuId) {
        menuRoleService.deleteByMenuId(menuId);
    }
}
