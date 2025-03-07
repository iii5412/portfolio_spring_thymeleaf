package com.portfolio.main.infrastructure.config.security.service;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.login.service.UserQueryService;
import com.portfolio.main.presentation.rest.account.login.exception.InvalidLoginId;
import com.portfolio.main.infrastructure.config.security.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
