package com.portfolio.main.application.program.service;

import com.portfolio.main.application.program.dto.ProgramDto;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.presentation.rest.TestAuth;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ProgramManageServiceTest {

    @Autowired
    private ProgramManageService programManageService;

    @Autowired
    private ProgramQueryService programQueryService;

    @Autowired
    private EntityManager entityManager;

    private final int testProgramMaxCnt = 20;
    private final String testProgramName = "테스트프로그램";
    private final String testProgramUrl = "/testProgram";
    private ProgramDto testProgram;
    private String testUserId = TestAuth.USER_ADMIN;

    @BeforeEach
    void setupData() {
        //given
        IntStream.range(1, testProgramMaxCnt + 1).forEach(i -> {
            final CreateProgram createProgram = new CreateProgram(this.testProgramName + "_" + i, this.testProgramUrl + "_" + i, testUserId);
            final Long testProgramId = programManageService.create(createProgram);
            if (i == 1) {
                testProgram = programQueryService.findById(testProgramId);
            }
        });
    }


    @Test
    void save() {
        //given
        final Long savedId = testProgram.getId();

        //when
        final ProgramDto findProgram = programQueryService.findById(savedId);

        //then
        assertEquals(testProgram.getUrl(), findProgram.getUrl());
    }

    @Test
    void deleteProgramHasProgramId() {
        //when
        programManageService.delete(testProgram.getId());

        //then
        final SearchProgram searchProgram = SearchProgram.builder().size(20).build();
        final PageResult<ProgramDto> programs = programQueryService.selectManageProgram(searchProgram);

        //expect
        assertEquals(19, programs.getResult().size());
    }

    @Test
    void selectAllProgram() {
        int size = 10;
        //when
        final PageResult<ProgramDto> programs = programQueryService.selectManageProgram(SearchProgram.builder().size(size).build());

        //expect
        assertEquals(size, programs.getResult().size());
    }

    @Test
    void selectProgram() {
        final SearchProgram searchProgram = SearchProgram
                .builder()
                .size(20)
                .sort(List.of(Sort.Direction.DESC))
                .sortFields(List.of("updatedAt"))
                .build();

        final PageResult<ProgramDto> programPageResult = programQueryService.selectManageProgram(searchProgram);

        assertEquals(20, programPageResult.getResult().size());
    }

    @Test
    void selectProgramWithNameAndUrl() {
        final SearchProgram searchProgram = SearchProgram.builder().programName("1").url("3").build();
        final PageResult<ProgramDto> programPageResult = programQueryService.selectManageProgram(searchProgram);
        final List<ProgramDto> result = programPageResult.getResult();

        assertEquals(1, result.size());
    }

    @Test
    void editProgram() {
        final EditProgram editProgram = EditProgram.builder()
                .id(testProgram.getId())
                .programName("editProgram")
                .url("/editProgram")
                .editUserLoginId("admin")
                .build();
        programManageService.edit(editProgram);

        entityManager.flush();
        entityManager.clear();

        final ProgramDto editedProgram = programQueryService.findById(testProgram.getId());

        assertEquals("editProgram", editedProgram.getProgramName());
        assertEquals("/editProgram", editedProgram.getUrl());
        assertEquals("admin", editedProgram.getLastUpdatedByUser().getLoginId());

    }
}
