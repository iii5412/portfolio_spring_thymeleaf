package com.portfolio.main.controller.api.menu;

import com.portfolio.main.controller.api.menu.request.EditMenuRequest;
import com.portfolio.main.controller.api.menu.response.MenuResponse;
import com.portfolio.main.controller.api.response.SuccResponse;
import com.portfolio.main.menu.domain.Menu;
import com.portfolio.main.menu.dto.menu.CreateMenu;
import com.portfolio.main.menu.dto.menu.EditMenu;
import com.portfolio.main.menu.dto.menu.MenuDto;
import com.portfolio.main.menu.dto.menu.SearchMenu;
import com.portfolio.main.menu.service.MenuService;
import com.portfolio.main.util.page.PageResult;
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
    public ResponseEntity<List<MenuResponse>> getAllMenuByUserRole() {
        final List<MenuDto> allWithProgram = menuService.findAllMenusByUserRole();
        final List<MenuResponse> responseList = allWithProgram.stream().map(MenuResponse::new).toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/flat")
    public ResponseEntity<PageResult<MenuResponse>> getAllMenuFlat(@RequestBody SearchMenu searchMenu) {
        final List<MenuDto> menuDtos = menuService.selectMenu(searchMenu);
        final List<MenuResponse> menuResponseList = menuDtos.stream().map(MenuResponse::new).toList();

        final PageResult<MenuResponse> menuResponsePageResult = PageResult.<MenuResponse>builder()
                .result(menuResponseList)
                .build();

        return ResponseEntity.ok(menuResponsePageResult);
    }


    @PatchMapping("")
    public ResponseEntity<Long> edit(@AuthenticationPrincipal UserDetails userDetails, @RequestBody EditMenuRequest editMenuRequest) {
        final EditMenu editMenu = new EditMenu(editMenuRequest, userDetails.getUsername());

        final Long editedMenuId = menuService.edit(editMenu);
        return ResponseEntity.ok(editedMenuId);
    }


    @PostMapping("/")
    public ResponseEntity<Long> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateMenu createMenu) {
        createMenu.setCreateUserLoginId(userDetails.getUsername());
        final Long savedMenuId = menuService.saveMenu(createMenu);
        return ResponseEntity.ok(savedMenuId);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> delete(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok(new SuccResponse());
    }
}
