package com.portfolio.main.presentation.rest.program;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.main.infrastructure.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.presentation.rest.TestAuth;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.domain.model.menu.exception.ProgramNotFoundException;
import com.portfolio.main.application.program.service.ProgramApplicationService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProgramControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProgramApplicationService programApplicationService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TestAuth testAuth;

    private int maxTestDataCnt = 20;
    private String testProgramName = "테스트";
    private String testProgramUrl = "/testProgram";
    private String requestMapping = "/program";
    private Long testProgramId;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String token = "";

    @BeforeEach
    void setup() {
        //given
        IntStream.range(1, maxTestDataCnt + 1).forEach(i -> {
            final CreateProgram createProgram = new CreateProgram(this.testProgramName + "_" + i, this.testProgramUrl + "_" + i, "admin");
            final Long savedTestProgramId = programApplicationService.create(createProgram);

            if (i == 1)
                testProgramId = savedTestProgramId;
        });

        token = testAuth.setUserAdminAndGetToken();
    }

    @Test
    void when_select_all_programs() throws Exception {
        mockMvc.perform(
                        get(requestMapping + "?page=1&size=20&sortFields=updatedAt&sorts=DESC")
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").exists())
                .andExpect(jsonPath("$.totalCount", greaterThanOrEqualTo(20)))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result", hasSize(20)))
                .andExpect(jsonPath("$.result[0].programName").value(testProgramName + "_20"))
                .andDo(print());
    }

    @Test
    void save() throws Exception {
        final CreateProgram createProgram = new CreateProgram("testProgram", "/test");

        final String createProgramString = objectMapper.writeValueAsString(createProgram);

        mockMvc.perform(
                        post(requestMapping)
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(createProgramString)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        //when
        final Program testProgram = programApplicationService.findById(testProgramId);

        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(requestMapping + "/" + testProgram.getId())
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                )
                .andExpect(status().isOk())
                .andDo(print());

        entityManager.flush();
        entityManager.clear();

        assertThrows(ProgramNotFoundException.class, () -> programApplicationService.findById(testProgram.getId()));

    }

    @Test
    void edit() throws Exception {
        final Program testProgram = programApplicationService.findById(testProgramId);

        final EditProgram editProgram = EditProgram.builder()
                .id(testProgram.getId())
                .programName("editProgram")
                .url("editUrl")
                .build();

        final String editProgramString = objectMapper.writeValueAsString(editProgram);

        //then
        mockMvc.perform(
                        patch(requestMapping + "/")
                                .contentType(APPLICATION_JSON)
                                .cookie(new Cookie(JwtAuthenticationToken.TOKEN_NAME, token))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(editProgramString)
                )
                .andExpect(status().isOk())
                .andDo(print());

        entityManager.flush();
        entityManager.clear();

        final Program editedProgram = programApplicationService.findById(testProgramId);

        assertEquals("editProgram", editedProgram.getProgramName());
        assertEquals("editUrl", editedProgram.getUrl());
    }


}
