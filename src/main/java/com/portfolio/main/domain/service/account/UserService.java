package com.portfolio.main.domain.service.account;

import com.portfolio.main.application.login.dto.UserCreateDto;
import com.portfolio.main.application.login.exception.InvalidLoginId;
import com.portfolio.main.application.login.exception.InvalidLoginPassword;
import com.portfolio.main.application.login.exception.InvalidUserId;
import com.portfolio.main.application.role.dto.RoleDto;
import com.portfolio.main.domain.model.account.Role;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.model.account.UserRole;
import com.portfolio.main.domain.repository.account.UserRepository;
import com.portfolio.main.domain.service.account.role.RoleService;
import com.portfolio.main.domain.service.account.userrole.UserRoleService;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자와 관련된 작업을 수행하는 서비스 클래스입니다.
 * 이 클래스는 필요한 UserRepository, UserRoleService, RoleService 및 BCryptPasswordEncoder를 주입받아서 사용하며,
 * 사용자를 찾는 검색, 사용자 등록, 로그인 정보의 검증 등 다양한 메서드를 제공합니다.
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final RoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository
            , UserRoleService userRoleService
            , RoleService roleService
            , BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByLoginId(String loginId) throws InvalidLoginId {
        return userRepository.findByLoginId(loginId).orElseThrow(InvalidLoginId::new);
    }

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(InvalidUserId::new);
    }


    public List<User> findAllUser() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Long signUp(UserCreateDto userCreateDto, RoleDto roleDto) {
        final User newUser = new User(
                userCreateDto.getLoginId(),
                passwordEncoder.encode(userCreateDto.getLoginPw()),
                userCreateDto.getUsername(),
                null
        );

        final Role role = roleService.findById(roleDto.getId());
        final UserRole userRole = userRoleService.createUserRole(newUser, role);
        newUser.addUserRole(userRole);
        final User savedUser = userRepository.save(newUser);
        return savedUser.getId();
    }

    public void validateLoginCredentials(String loginId, String loginPw) throws InvalidLoginId, InvalidLoginPassword {
        final User user = userRepository.findByLoginId(loginId).orElseThrow(InvalidLoginId::new);

        if(!isPasswordMatching(loginPw, user.getLoginPw())){
            throw new InvalidLoginPassword();
        }
    }

    private boolean isPasswordMatching(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

}
