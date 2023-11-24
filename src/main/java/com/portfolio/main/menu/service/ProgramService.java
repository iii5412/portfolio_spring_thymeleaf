package com.portfolio.main.menu.service;

import com.portfolio.main.account.domain.Role;
import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.user.repository.RoleRepository;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.account.user.service.RoleCode;
import com.portfolio.main.menu.domain.Program;
import com.portfolio.main.menu.dto.program.CreateProgram;
import com.portfolio.main.menu.dto.program.EditProgram;
import com.portfolio.main.menu.dto.program.SearchProgram;
import com.portfolio.main.menu.exception.ProgramNotFoundException;
import com.portfolio.main.menu.repository.ProgramRepository;
import com.portfolio.main.util.page.PageResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProgramService {
    private final RoleRepository roleRepository;
    private final ProgramRepository programRepository;
    private final MyUserDetailsService userDetailsService;

    public Program findById(Long id) throws ProgramNotFoundException {
        return programRepository.findById(id).orElseThrow(ProgramNotFoundException::new);
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public PageResult<Program> selectProgram(SearchProgram searchProgram) {
        PageRequest pageable = PageRequest.of(searchProgram.getPage() - 1, searchProgram.getSize(), searchProgram.getSort());
        return programRepository.selectProgram(searchProgram, pageable);
    }

    public Long save(CreateProgram createProgram) {
        final User createUser = userDetailsService.findUserByLoginId(createProgram.getCreateUserLoginId());
        final Program program = new Program(createProgram.getProgramName(), createProgram.getUrl(), createUser);

        final Program savedProgram = programRepository.save(program);
        return savedProgram.getId();
    }

    public void delete(Long programId) {
        programRepository.deleteProgram(programId);
    }

    public void edit(Long programId, EditProgram editProgram) {
        final Program targetProgram = programRepository.findById(programId).orElseThrow(ProgramNotFoundException::new);
        final User modifier = userDetailsService.findUserByLoginId(editProgram.getEditUserLoginId());

        Role role = null;
        if(StringUtils.hasText(editProgram.getRoleCode()))
            role = roleRepository.findByRoleCode(RoleCode.valueOf(editProgram.getRoleCode()));


        targetProgram.edit(editProgram, role, modifier);
        programRepository.save(targetProgram);
    }
}
