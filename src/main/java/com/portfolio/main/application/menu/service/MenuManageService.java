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

    /**
     * 일련의 단계를 거쳐 SearchMenu params를 기반으로 계층적 MenuManageDto 객체 List를 반환한다.
     * - 1단계: 전체 메뉴를 평탄화된 (모든 메뉴가 동일한 레벨에 있는) MenuDto List로 받아온다.
     * - 2단계: SearchMenu params를 기반으로 해당 평탄화된 메뉴 List 내에서 필요한 항목들만 필터링한다.
     * - 3단계: 필터된 각각의 MenuDto들을 MenuManageDto로 변환한다. 이 과정에서 각각 메뉴 진행상의 메뉴들은 같은 level에 위치한다(아직 계층적 구조화 X)
     * - 4단계: Convert된 메뉴 객체들을 기존 계층적 구조대로 재배열하고 결과를 반환한다.
     */
    public List<MenuManageDto> selectMenu(SearchMenu searchMenu) {
        // Step 1
        final List<MenuDto> flatMenus = menuQueryService.selectMenuFlat();
        // Step 2
        final List<MenuDto> filteredMenus = filterBySearchMenu(flatMenus, searchMenu);
        // Step 3
        final List<MenuManageDto> flattenMenuManageDtos = filteredMenus.stream()
                .map(this::menuDtoToMenuManageDto)
                .filter(menuManageDto -> !menuManageDto.hasUpperMenu())
                .toList();
        // Step 4
        final List<MenuManageDto> menuManageDtos = rebuildHierarchyFromFlatMenuList(flattenMenuManageDtos);

        return menuManageDtos;
    }
    /**
     * 특정 ID를 가진 MenuManageDto 객체를 찾아 반환하는 메서드.
     *
     * @param id 찾고자 하는 메뉴의 Unique한 식별자인 ID.
     * @return 찾은 MenuManageDto 객체. 만약 해당 ID 값을 가진 메뉴가 없을 경우 null 반환.
     */
    public MenuManageDto findById(Long id) {
        final MenuDto findMenu = menuQueryService.findById(id);
        return menuDtoToMenuManageDto(findMenu);
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
                        new MenuManageDto(menuDto)).orElseGet(() -> new MenuManageDto(menuDto));
    }

    /**
     * 평탄화된 MenuManageDto 컬렉션을 계층 구조로 변경하여 반환한다.
     *
     * @param flattenedMenus
     * @return
     */
    private List<MenuManageDto> rebuildHierarchyFromFlatMenuList(List<MenuManageDto> flattenedMenus) {
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
