package com.portfolio.main.infrastructure.config.security.service;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.login.service.UserQueryService;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.application.login.exception.InvalidLoginId;
import com.portfolio.main.application.login.exception.InvalidLoginPassword;
import com.portfolio.main.application.login.exception.InvalidUserId;
import com.portfolio.main.domain.repository.account.UserRepository;
import com.portfolio.main.domain.service.account.UserService;
import com.portfolio.main.infrastructure.config.security.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserQueryService userQueryService;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws InvalidLoginId {
        final UserDto findUser = userQueryService.findByLoginId(loginId);
        return new MyUserDetails(findUser);
    }
}
