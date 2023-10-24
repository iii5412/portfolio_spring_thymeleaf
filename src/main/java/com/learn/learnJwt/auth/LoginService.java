package com.learn.learnJwt.auth;

import com.learn.learnJwt.domain.User;
import com.learn.learnJwt.security.jwt.provider.JwtProvider;
import com.learn.learnJwt.user.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private MyUserDetailsService userService;
    private JwtProvider jwtProvider;

    public JwtResponse login(String username, String password) {
        final User user = userService.validateUser(username, password);

        if (user != null) {
            final String jwt = jwtProvider.createToken(user);
            return new JwtResponse(jwt);
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }
}
