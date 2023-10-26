package com.portfolio.main.security.jwt.filters;

import com.portfolio.main.account.dto.JwtResponse;
import com.portfolio.main.security.jwt.util.TokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
