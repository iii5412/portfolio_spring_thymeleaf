package com.portfolio.main.controller.api.program;

import com.portfolio.main.config.security.MyUserDetails;
import com.portfolio.main.controller.api.program.response.ProgramResponse;
import com.portfolio.main.controller.api.response.SuccResponse;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.program.CreateProgram;
import com.portfolio.main.menu.dto.program.EditProgram;
import com.portfolio.main.menu.dto.program.SearchProgram;
import com.portfolio.main.menu.service.ProgramService;
import com.portfolio.main.util.page.PageResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProgramController {

    private final ProgramService programService;
    private MyUserDetails userDetails;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @ModelAttribute
    public void modelAttribute() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (MyUserDetails) authentication.getPrincipal();
    }

    @GetMapping("")
    public ResponseEntity<PageResult<ProgramResponse>> getAllProgram(SearchProgram searchProgram) {
        final PageResult<Program> programPageResult = programService.selectProgram(searchProgram);
        final List<ProgramResponse> programResponses = programPageResult.getResult().stream().map(ProgramResponse::new).toList();
        final PageResult<ProgramResponse> programResponsePageResult = PageResult.<ProgramResponse>builder()
                .result(programResponses)
                .totalCount(programPageResult.getTotalCount())
                .build();
        return ResponseEntity.ok(programResponsePageResult);
    }

    @PostMapping("")
    public ResponseEntity<Long> create(
            @RequestBody CreateProgram createProgram
    ) {
        createProgram.setCreateUserLoginId(userDetails.getUsername());
        createProgram.validate();
        final Long savedId = programService.create(createProgram);
        return ResponseEntity.ok(savedId);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<?> delete(@PathVariable Long programId) {
        programService.delete(programId);
        return ResponseEntity.ok(new SuccResponse());
    }

    @PatchMapping("/{programId}")
    public ResponseEntity<Long> edit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long programId, @RequestBody EditProgram editProgram) {
        editProgram.setEditUserLoginId(userDetails.getUsername());
        programService.edit(programId, editProgram);
        return ResponseEntity.ok(programId);
    }


}
