package com.portfolio.main.domain.service.menu.program;

import com.portfolio.main.application.program.dto.CreateProgram;
import com.portfolio.main.application.program.dto.EditProgram;
import com.portfolio.main.application.program.dto.SearchProgram;
import com.portfolio.main.application.program.exception.DuplicatedProgramUrlException;
import com.portfolio.main.common.util.page.PageResult;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.menu.Program;
import com.portfolio.main.domain.model.menu.exception.ProgramNotFoundException;
import com.portfolio.main.domain.repository.menu.ProgramRepository;
import com.portfolio.main.domain.service.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProgramService {
    private final UserService userService;
    private final ProgramRepository programRepository;

    public Program findById(Long id) throws ProgramNotFoundException {
        return programRepository.findById(id).orElseThrow(ProgramNotFoundException::new);
    }

    public Program findByUrl(String url) throws ProgramNotFoundException {
        return programRepository.findByUrl(url);
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public PageResult<Program> selectProgram(SearchProgram searchProgram, PageRequest pageRequest, boolean existImmutable) {
        return programRepository.selectProgram(searchProgram, pageRequest, existImmutable);
    }

    @Transactional
    public Long createProgram(CreateProgram createProgram) throws DuplicatedProgramUrlException {
        final String createUserLoginId = createProgram.getCreateUserLoginId();

        final User createUser = userService.findByLoginId(createUserLoginId);

        final Program newProgram = new Program(createProgram.getProgramName(), createProgram.getUrl(), createUser);
        try {
            final Program savedProgram = programRepository.save(newProgram);
            return savedProgram.getId();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedProgramUrlException();
        }
    }

    @Transactional
    public void edit(EditProgram editProgram) {
        final Program targetProgram = programRepository.findById(editProgram.getId()).orElseThrow(ProgramNotFoundException::new);
        final User modifier = userService.findByLoginId(editProgram.getEditUserLoginId());

        targetProgram.edit(editProgram.getProgramName(), editProgram.getUrl(), modifier);

        programRepository.save(targetProgram);
    }

    public void delete(Long id) {
        programRepository.deleteProgram(id);
    }
}
