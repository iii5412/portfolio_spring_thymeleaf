package com.portfolio.main.security.jwt.provider;

import com.portfolio.main.security.jwt.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationProvider(JwtProvider jwtProvider, UserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        final String token = jwtAuthenticationToken.getToken();

        if (!jwtProvider.validateToken(token)) {
            throw new BadCredentialsException("Invalid token");
        }
        final String username = jwtProvider.getLoginIdFromToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new JwtAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
