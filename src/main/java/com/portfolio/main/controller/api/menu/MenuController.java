package com.portfolio.main.controller.api.menu;

import com.portfolio.main.controller.api.menu.response.MenuResponse;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.dto.menu.CreateMenu;
import com.portfolio.main.menu.dto.menu.EditMenu;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MenuResponse>> getAllMenu() {
        final List<Menu> allWithProgram = menuService.findAllWithProgram();
        final List<MenuResponse> responseList = allWithProgram.stream().map(MenuResponse::new).toList();
        return ResponseEntity.ok(responseList);
    }


    @GetMapping("/manage")
    public ResponseEntity<List<MenuResponse>> selectMenu(SearchMenu searchMenu) {
        final List<Menu> allWithProgram = menuService.selectMenu(searchMenu);
        final List<MenuResponse> responseList = allWithProgram.stream().map(MenuResponse::new).toList();
        return ResponseEntity.ok(responseList);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<Long> edit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long menuId, @RequestBody EditMenu editMenu) {
        editMenu.setEditUserLoginId(userDetails.getUsername());
        menuService.edit(menuId, editMenu);
        return ResponseEntity.ok(menuId);
    }


    @PostMapping("/")
    public ResponseEntity<Long> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateMenu createMenu) {
        createMenu.setCreateUserLoginId(userDetails.getUsername());
        final Long savedMenuId = menuService.saveMenu(createMenu);
        return ResponseEntity.ok(savedMenuId);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> delete(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok().build();
    }
}
