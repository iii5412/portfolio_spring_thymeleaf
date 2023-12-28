package com.portfolio.main.presentation.rest.menu;

import com.portfolio.main.presentation.rest.menu.request.EditMenuRequest;
import com.portfolio.main.presentation.rest.menu.response.FolderMenusResponse;
import com.portfolio.main.presentation.rest.menu.response.MainMenuResponse;
import com.portfolio.main.presentation.rest.menu.response.ManageMenuResponse;
import com.portfolio.main.presentation.rest.response.SuccResponse;
import com.portfolio.main.application.menu.dto.CreateMenu;
import com.portfolio.main.application.menu.dto.EditMenu;
import com.portfolio.main.application.menu.dto.MenuDto;
import com.portfolio.main.application.menu.dto.SearchMenu;
import com.portfolio.main.application.menu.service.MenuApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/menu")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MenuController {

    private final MenuApplicationService menuApplicationService;

    @Autowired
    public MenuController(MenuApplicationService menuApplicationService) {
        this.menuApplicationService = menuApplicationService;
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<MainMenuResponse>> getAllMenuByUserRole() {
        final List<MenuDto> allWithProgram = menuApplicationService.selectAllMenusByUserRole();
        final List<MainMenuResponse> responseList = allWithProgram.stream().map(MainMenuResponse::new).toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ManageMenuResponse>> getAllMenuFlat(SearchMenu searchMenu) {
        final List<MenuDto> menuDtos = menuApplicationService.selectMenu(searchMenu);
        final List<ManageMenuResponse> mainMenuResponseList = menuDtos.stream().map(ManageMenuResponse::new).toList();
        return ResponseEntity.ok(mainMenuResponseList);
    }

    @GetMapping("/forderMenus")
    public ResponseEntity<List<FolderMenusResponse>> getFolderMenus() {
        final List<MenuDto> menuDtos = menuApplicationService.selectFolrderMenus();
        final List<FolderMenusResponse> folderMenusResponses = menuDtos.stream().map(FolderMenusResponse::new).toList();
        return ResponseEntity.ok(folderMenusResponses);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ManageMenuResponse> findMenu(@PathVariable Long id) {
        final MenuDto findMenu = menuApplicationService.findById(id);

        return ResponseEntity.ok(new ManageMenuResponse(findMenu));
    }


    @PatchMapping("")
    public ResponseEntity<Long> edit(@AuthenticationPrincipal UserDetails userDetails, @RequestBody EditMenuRequest editMenuRequest) {
        final EditMenu editMenu = new EditMenu(editMenuRequest, userDetails.getUsername());

        final Long editedMenuId = menuApplicationService.edit(editMenu);
        return ResponseEntity.ok(editedMenuId);
    }


    @PostMapping("/")
    public ResponseEntity<Long> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateMenu createMenu) {
        createMenu.setCreateUserLoginId(userDetails.getUsername());
        final Long savedMenuId = menuApplicationService.saveMenu(createMenu);
        return ResponseEntity.ok(savedMenuId);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> delete(@PathVariable Long menuId) {
        menuApplicationService.deleteMenu(menuId);
        return ResponseEntity.ok(new SuccResponse());
    }
}
