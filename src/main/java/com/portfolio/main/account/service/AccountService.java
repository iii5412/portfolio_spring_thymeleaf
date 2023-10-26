package com.portfolio.main.account.service;

import com.portfolio.main.account.dto.JwtResponse;
import com.portfolio.main.account.dto.UserRegist;
import com.portfolio.main.account.domain.User;
import com.portfolio.main.exception.BusiException;
import com.portfolio.main.security.jwt.provider.JwtProvider;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.account.user.service.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AccountService {
    private MyUserDetailsService userService;
    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public JwtResponse login(String loginId, String loginPw) {
        try {
            final User user = userService.validateUser(loginId, loginPw);
            final String jwt = jwtProvider.createToken(user);
            return new JwtResponse(jwt);
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (BusiException e) {
            throw e;
        }
    }

    public void signUp(UserRegist userRegist) {
        final User user = User.builder()
                .loginId(userRegist.getLoginId())
                .loginPw(userRegist.getLoginPw1())
                .username(userRegist.getUsername())
                .build();
        userRepository.save(user);
    }
}
