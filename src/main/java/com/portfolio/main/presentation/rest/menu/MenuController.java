package com.portfolio.main.presentation.rest.menu;

import com.portfolio.main.application.menu.dto.*;
import com.portfolio.main.application.menu.service.MenuManageService;
import com.portfolio.main.application.menurole.service.MenuRoleApplicationService;
import com.portfolio.main.presentation.rest.menu.request.EditMenuRequest;
import com.portfolio.main.presentation.rest.menu.response.FolderMenusResponse;
import com.portfolio.main.presentation.rest.menu.response.MainMenuResponse;
import com.portfolio.main.presentation.rest.menu.response.ManageMenuResponse;
import com.portfolio.main.presentation.rest.response.SuccResponse;
import com.portfolio.main.application.menu.service.MenuQueryService;
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

    private final MenuManageService menuManageService;
    private final MenuQueryService menuQueryService;

    @Autowired
    public MenuController(
            MenuManageService menuManageService
            , MenuQueryService menuQueryService
    ) {
        this.menuManageService = menuManageService;
        this.menuQueryService = menuQueryService;
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<MainMenuResponse>> getAllMenuByUserRole() {
        final List<MenuDto> allWithProgram = menuQueryService.selectAllMenusByUserRole();
        final List<MainMenuResponse> responseList = allWithProgram.stream().map(MainMenuResponse::new).toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ManageMenuResponse>> getAllMenuFlat(SearchMenu searchMenu) {
        final List<MenuManageDto> menuDtos = menuManageService.selectMenu(searchMenu);
        final List<ManageMenuResponse> mainMenuResponseList = menuDtos.stream().map(ManageMenuResponse::new).toList();
        return ResponseEntity.ok(mainMenuResponseList);
    }

    @GetMapping("/forderMenus")
    public ResponseEntity<List<FolderMenusResponse>> getFolderMenus() {
        final List<MenuDto> menuDtos = menuManageService.selectFolderMenus();
        final List<FolderMenusResponse> folderMenusResponses = menuDtos.stream().map(FolderMenusResponse::new).toList();
        return ResponseEntity.ok(folderMenusResponses);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ManageMenuResponse> findMenu(@PathVariable Long id) {
        final MenuManageDto findMenu = menuManageService.findById(id);

        return ResponseEntity.ok(new ManageMenuResponse(findMenu));
    }


    @PatchMapping("")
    public ResponseEntity<Long> edit(@AuthenticationPrincipal UserDetails userDetails, @RequestBody EditMenuRequest editMenuRequest) {
        final EditMenu editMenu = new EditMenu(editMenuRequest, userDetails.getUsername());

        final Long editedMenuId = menuManageService.editMenu(editMenu);
        return ResponseEntity.ok(editedMenuId);
    }


    @PostMapping("")
    public ResponseEntity<Long> createMenu(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateMenu createMenu) {
        createMenu.setCreateUserLoginId(userDetails.getUsername());
        createMenu.validate();
        final Long savedMenuId = menuManageService.createMenu(createMenu);
        return ResponseEntity.ok(savedMenuId);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> delete(@PathVariable Long menuId) {
        menuManageService.deleteMenu(menuId);
        return ResponseEntity.ok(new SuccResponse());
    }
}
