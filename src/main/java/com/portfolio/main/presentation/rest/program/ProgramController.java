package com.portfolio.main.presentation.rest.program;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.application.program.exception.InvalidCreateProgramException;
import com.portfolio.main.application.program.service.ProgramManageService;
import com.portfolio.main.application.program.service.ProgramQueryService;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.infrastructure.config.security.MyUserDetails;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
import com.portfolio.main.presentation.rest.program.response.ProgramResponse;
import com.portfolio.main.presentation.rest.request.IdRequest;
import com.portfolio.main.presentation.rest.response.SuccResponse;
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

    private final ProgramQueryService programQueryService;
    private final ProgramManageService programManageService;
    private MyUserDetails userDetails;

    @Autowired
    public ProgramController(
            ProgramQueryService programQueryService
            , ProgramManageService programManageService
    ) {
        this.programQueryService = programQueryService;
        this.programManageService = programManageService;
    }

    @ModelAttribute
    public void modelAttribute() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (MyUserDetails) authentication.getPrincipal();
    }

    @GetMapping("")
    public ResponseEntity<List<ProgramResponse>> getAllProgram() {
        final List<ProgramDto> allProgram = programQueryService.findAll();
        return ResponseEntity.ok(allProgram.stream().map(ProgramResponse::new).toList());
    }

    @GetMapping("/manage")
    public ResponseEntity<PageResult<ProgramResponse>> getAllManageProgram(SearchProgram searchProgram) {
        final PageResult<ProgramDto> programPageResult = programQueryService.selectManageProgram(searchProgram);
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
        final Long savedId = programManageService.create(createProgram);
        return ResponseEntity.ok(savedId);
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@RequestBody IdRequest idRequest) {
        programManageService.delete(idRequest.getId());
        return ResponseEntity.ok(new SuccResponse());
    }

    @PatchMapping("")
    public ResponseEntity<Long> edit(@RequestBody EditProgram editProgram) {
        editProgram.setEditUserLoginId(userDetails.getUsername());
        programManageService.edit(editProgram);
        return ResponseEntity.ok(editProgram.getId());
    }


}
