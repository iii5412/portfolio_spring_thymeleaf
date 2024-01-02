package com.portfolio.main.application.login.service;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.service.account.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserQueryService {
    private final UserService userService;

    public UserQueryService(UserService userService) {
        this.userService = userService;
    }

    public List<UserDto> findAll() {
        return userService.findAllUser().stream().map(UserDto::new).toList();
    }

    public UserDto findById(Long id){
        final User user = userService.findByUserId(id);
        return new UserDto(user);
    }

    public UserDto findByLoginId(String loginId) {
        final User byLoginId = userService.findByLoginId(loginId);
        return new UserDto(byLoginId);
    }
}
