package com.learn.learnJwt.security.jwt.filters;

import com.learn.learnJwt.auth.JwtResponse;
import com.learn.learnJwt.security.jwt.JwtAuthenticationToken;
import com.learn.learnJwt.security.jwt.util.TokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenRefreshFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {
            TokenUtil.setTokenToResponse(response, new JwtResponse((String)authentication.getCredentials()));
        }

        filterChain.doFilter(request, response);
    }

}
