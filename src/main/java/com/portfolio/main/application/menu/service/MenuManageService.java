package com.portfolio.main.application.menu.service;

import com.portfolio.main.application.menu.dto.*;
import com.portfolio.main.application.menu.exception.CannotDeleteMenuWithSubmenusException;
import com.portfolio.main.application.menu.exception.UpperMenuNotFoundException;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.menurole.service.MenuRoleApplicationService;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.application.role.service.RoleApplicationService;
import com.portfolio.main.domain.model.account.exception.RoleNotFoundException;
import com.portfolio.main.domain.model.account.type.RoleCode;
import com.portfolio.main.domain.model.menu.exception.MenuNotFoundException;
import com.portfolio.main.domain.service.menu.menu.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
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
        final List<MenuDto> flatMenus = menuQueryService.selectMenuFlat();
        final List<MenuDto> filterMenus = filterBySearchMenu(flatMenus, searchMenu);

        final List<MenuManageDto> menuManageDtoList = filterMenus.stream()
                .map(this::menuDtoToMenuManageDto)
                .toList();

        return rebuildHierarchyFromFlatMenuList(menuManageDtoList);
    }

    public MenuManageDto findById(Long id) {
        final MenuDto byId = menuQueryService.findById(id);
        return menuDtoToMenuManageDto(byId);
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
                    .filter(menu -> Objects.equals(menu.getUpperMenuId(), menuDto.getId()))
                    .toList();

        }

        return Stream
                .concat(filteredMenus.stream(), filteredMenusChildren.stream())
                .distinct().toList();
    }

    private MenuManageDto menuDtoToMenuManageDto(MenuDto menuDto) {
        return menuDto.getRoles().stream()
                .map(roleDto -> roleApplicationService.findById(roleDto.getId()))
                .min(Comparator.comparing(RoleDto::getLevel))
                .map(roleLevelDto ->
                        new MenuManageDto(menuDto))
                .orElseGet(() -> new MenuManageDto(menuDto));
    }

    /**
     * 평탄화된 MenuManageDto 컬렉션을 계층 구조로 변경하여 반환한다.
     *
     * @param flattenedMenus
     * @return
     */
    public List<MenuManageDto> rebuildHierarchyFromFlatMenuList(List<MenuManageDto> flattenedMenus) {
        final Map<Long, MenuManageDto> menuMap = flattenedMenus.stream()
                .collect(Collectors.toMap(MenuManageDto::getId, menu -> menu));

        List<MenuManageDto> topMenus = new ArrayList<>();

        for (MenuManageDto menuManageDto : flattenedMenus) {
            if (!menuManageDto.hasUpperMenu()) {
                topMenus.add(menuManageDto);
            } else {
                final MenuManageDto parent = menuMap.get(menuManageDto.getUpperMenuId());
                if (parent != null) {
                    parent.addSubMenu(menuManageDto);
                }
            }
        }

        return topMenus;
    }

}
