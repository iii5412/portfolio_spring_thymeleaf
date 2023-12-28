package com.portfolio.main.presentation.rest.program;

import com.portfolio.main.infrastructure.config.security.MyUserDetails;
import com.portfolio.main.presentation.rest.program.response.ProgramResponse;
import com.portfolio.main.presentation.rest.request.IdRequest;
import com.portfolio.main.presentation.rest.response.SuccResponse;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.application.program.service.ProgramApplicationService;
import com.portfolio.main.common.util.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProgramController {

    private final ProgramApplicationService programApplicationService;
    private MyUserDetails userDetails;

    @Autowired
    public ProgramController(ProgramApplicationService programApplicationService) {
        this.programApplicationService = programApplicationService;
    }

    @ModelAttribute
    public void modelAttribute() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (MyUserDetails) authentication.getPrincipal();
    }

    @GetMapping("")
    public ResponseEntity<PageResult<ProgramResponse>> getAllProgram(SearchProgram searchProgram) {
        final PageResult<Program> programPageResult = programApplicationService.selectProgram(searchProgram);
        final List<ProgramResponse> programResponses = programPageResult.getResult().stream().map(ProgramResponse::new).toList();
        final PageResult<ProgramResponse> programResponsePageResult = PageResult.<ProgramResponse>builder()
                .result(programResponses)
                .totalCount(programPageResult.getTotalCount())
                .page(searchProgram.getPage())
                .size(searchProgram.getSize())
                .build();
        return ResponseEntity.ok(programResponsePageResult);
    }

    @GetMapping("/manage")
    public ResponseEntity<PageResult<ProgramResponse>> getAllManageProgram(SearchProgram searchProgram) {
        final PageResult<Program> programPageResult = programApplicationService.selectManageProgram(searchProgram);
        final List<ProgramResponse> programResponses = programPageResult.getResult().stream().map(ProgramResponse::new).toList();
        final PageResult<ProgramResponse> programResponsePageResult = PageResult.<ProgramResponse>builder()
                .result(programResponses)
                .totalCount(programPageResult.getTotalCount())
                .page(searchProgram.getPage())
                .size(searchProgram.getSize())
                .build();
        return ResponseEntity.ok(programResponsePageResult);
    }

    @PostMapping("")
    public ResponseEntity<Long> create(
            @RequestBody CreateProgram createProgram
    ) {
        createProgram.setCreateUserLoginId(userDetails.getUsername());
        createProgram.validate();
        final Long savedId = programApplicationService.create(createProgram);
        return ResponseEntity.ok(savedId);
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@RequestBody IdRequest idRequest) {
        programApplicationService.delete(idRequest.getId());
        return ResponseEntity.ok(new SuccResponse());
    }

    @PatchMapping("")
    public ResponseEntity<Long> edit(@RequestBody EditProgram editProgram) {
        editProgram.setEditUserLoginId(userDetails.getUsername());
        programApplicationService.edit(editProgram);
        return ResponseEntity.ok(editProgram.getId());
    }


}
