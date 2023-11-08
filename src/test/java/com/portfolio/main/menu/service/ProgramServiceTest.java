package com.portfolio.main.menu.service;

import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.program.DeleteProgram;
import com.portfolio.main.menu.dto.program.CreateProgram;
import com.portfolio.main.menu.dto.program.SearchProgram;
import com.portfolio.main.util.page.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProgramServiceTest {

    @Autowired
    private ProgramService programService;

    private final int testProgramMaxCnt = 20;
    private final String testProgramName = "테스트프로그램";
    private final String testProgramUrl = "/testProgram";
    private Program testProgram;

    @BeforeEach
    void setupData() {
        //given
        IntStream.range(1, testProgramMaxCnt + 1).forEach(i -> {
            final CreateProgram createProgram = new CreateProgram(this.testProgramName + "_" + i, this.testProgramUrl + "_" + i);
            final Long testProgramId = programService.save(createProgram);
            if (i == 1) {
                testProgram = programService.findById(testProgramId);
            }
        });
    }


    @Test
    void save() {
        //given
        final Long savedId = testProgram.getId();

        //when
        final Program findProgram = programService.findById(savedId);

        //then
        assertEquals(testProgram.getUrl(), findProgram.getUrl());
    }

    @Test
    void deleteProgramHasProgramId() {
        //when
        final DeleteProgram deleteProgram = DeleteProgram.builder().id(testProgram.getId()).build();
        programService.delete(deleteProgram);

        //then
        final SearchProgram searchProgram = SearchProgram.builder().build();
        final PageResult<Program> programs = programService.selectProgram(searchProgram);

        //expect
        assertEquals(19, programs.getResult().size());
    }

    @Test
    void deleteProgramHasProgramNameAndUrl() {
        programService.delete(DeleteProgram.builder().programName("3").build());
        final PageResult<Program> programs = programService.selectProgram(SearchProgram.builder().programName("3").build());

        assertEquals(0, programs.getResult().size());
    }

    @Test
    void selectAllProgram() {
        //when
        final PageResult<Program> programs = programService.selectProgram(SearchProgram.builder().programName(testProgramName).build());

        //expect
        assertEquals(testProgramMaxCnt, programs.getResult().size());
    }

    @Test
    void selectProgram() {
        final SearchProgram searchProgram = SearchProgram
                .builder()
                .size(20)
                .sort(List.of(Sort.Direction.DESC))
                .sortFields(List.of("updatedAt"))
                .build();

        final PageResult<Program> programPageResult = programService.selectProgram(searchProgram);

        assertEquals(20, programPageResult.getResult().size());
    }

    @Test
    void selectProgramWithNameAndUrl() {
        final SearchProgram searchProgram = SearchProgram.builder().programName("1").url("3").build();
        final PageResult<Program> programPageResult = programService.selectProgram(searchProgram);
        final List<Program> result = programPageResult.getResult();

        assertEquals(1, result.size());
    }
}
