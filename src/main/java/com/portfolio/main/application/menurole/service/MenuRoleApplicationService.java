package com.portfolio.main.application.menurole.service;

import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.service.MenuQueryService;
import com.portfolio.main.application.menurole.dto.MenuRoleDto;
import com.portfolio.main.application.role.service.RoleApplicationService;
import com.portfolio.main.domain.service.account.role.RoleService;
import com.portfolio.main.domain.service.menu.menu.MenuService;
import com.portfolio.main.domain.service.menu.menurole.MenuRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MenuRoleApplicationService {
    private final MenuRoleService menuRoleService;

    public List<MenuRoleDto> findByMenu(MenuDto menuDto) {
        return menuRoleService.findByMenuId(menuDto.getId()).stream().map(MenuRoleDto::new).toList();
    }

}
