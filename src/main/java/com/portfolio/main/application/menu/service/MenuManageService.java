package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.menu.dto.*;
import com.portfolio.main.application.menu.dto.factory.MenuDtoFactory;
import com.portfolio.main.application.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.application.menu.exception.UpperMenuNotFoundException;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.menurole.service.MenuRoleApplicationService;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.dto.RoleLevelDto;
import com.portfolio.main.application.role.service.RoleApplicationService;
import com.portfolio.main.domain.model.account.exception.RoleNotFoundException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.exception.MenuNotFoundException;
import com.portfolio.main.domain.service.menu.menu.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
@Transactional
public class MenuManageService {
    private final MenuQueryService menuQueryService;
    private final MenuService menuService;
    private final MenuRoleApplicationService menuRoleApplicationService;
    private final RoleApplicationService roleApplicationService;

    public MenuManageService(
            MenuQueryService menuQueryService
            , MenuService menuService
            , MenuRoleApplicationService menuRoleApplicationService
            , RoleApplicationService roleApplicationService
    ) {
        this.menuQueryService = menuQueryService;
        this.menuService = menuService;
        this.menuRoleApplicationService = menuRoleApplicationService;
        this.roleApplicationService = roleApplicationService;
    }

    public List<MenuManageDto> selectMenu(SearchMenu searchMenu) {
        final List<MenuDto> menus = menuQueryService.selectMenu();
        final List<MenuDto> filterMenus = filterBySearchMenu(menus, searchMenu);

        final List<MenuManageDto> menuManageDtoList = filterMenus.stream()
                .map(fm -> fm.getRoles().stream()
                        .map(roleDto -> roleApplicationService.findByIdFlat(roleDto.getId()))
                        .min(Comparator.comparing(RoleLevelDto::getLevel))
                        .map(roleLevelDto ->
                                new MenuManageDto(fm, roleLevelDto.getRoleCode()))
                        .orElseGet(() -> new MenuManageDto(fm, null))
                ).toList();

//
//        final List<MenuRoleDto> topMenuRolesForMenus = menuRoleApplicationService.createTopMenuRolesForMenus(filterMenus.stream().map(MenuDto::getId).toList());
//        final List<MenuManageDto> menuManageDtoList = topMenuRolesForMenus.stream()
//                .map(mr -> new MenuManageDto(mr.getMenuDto(), mr.getRoleDto().getRoleCode()))
//                .toList();
        MenuDtoFactory<MenuManageDto> menuDtoFactory = MenuManageDto::new;
        return menuQueryService.rebuildHierarchyFromFlatMenuList(menuManageDtoList, menuDtoFactory);
    }

    public List<MenuDto> selectFolderMenus() {
        final List<MenuDto> menus = menuQueryService.selectMenu();
        return menus.stream().filter(MenuDto::isFolder).toList();
    }

    public Long createMenu(CreateMenu createMenu) throws UpperMenuNotFoundException, RoleNotFoundException {
        return menuService.createMenu(createMenu);
    }

    public void deleteMenu(Long menuId) throws MenuNotFoundException, CannotDeleteMenuWithSubmenusException {
        menuService.deleteById(menuId);
    }

    public Long editMenu(EditMenu editMenu) throws MenuNotFoundException {
        return menuService.edit(editMenu);
    }

    private List<MenuDto> filterBySearchMenu(List<MenuDto> menus, SearchMenu searchMenu) {
        List<MenuDto> filteredMenus = menus.stream()
                .filter(menuDto -> {
                    if (searchMenu.getId() != null) {
                        return Objects.equals(menuDto.getId(), searchMenu.getId());
                    } else {
                        return true;
                    }
                })
                .filter(menuDto -> {
                    if (searchMenu.getMenuName() != null) {
                        return menuDto.getMenuName().contains(searchMenu.getMenuName());
                    } else {
                        return true;
                    }
                })
                .filter(menuDto -> {
                    if (searchMenu.getRoleCode() != null) {
                        final List<MenuRoleDto> menuRoleDtos = menuRoleApplicationService.findByMenu(menuDto);
                        if (menuRoleDtos.isEmpty()) {
                            return false;
                        } else {
                            return menuRoleDtos.stream()
                                    .map(mr -> {
                                        final Long roleId = mr.getMenuDto().getId();
                                        final RoleDto roleDto = roleApplicationService.findById(roleId);
                                        return roleDto;
                                    })
                                    .anyMatch(roleDto -> roleDto.getRoleCode() == RoleCode.valueOf(searchMenu.getRoleCode()));
                        }
                    }

                    return true;
                }).toList();


        List<MenuDto> filteredMenusChildren = new ArrayList<>();
        for (MenuDto menuDto : filteredMenus) {
            filteredMenusChildren = menus.stream()
                    .filter(MenuDto::hasUpperMenu)
                    .filter(menu -> Objects.equals(menu.getUpperMenu().getId(), menuDto.getId()))
                    .toList();

        }

        return Stream
                .concat(filteredMenus.stream(), filteredMenusChildren.stream())
                .distinct().toList();
    }

}
