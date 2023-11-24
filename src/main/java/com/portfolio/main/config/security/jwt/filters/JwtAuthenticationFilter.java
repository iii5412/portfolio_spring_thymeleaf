package com.portfolio.main.config.security.jwt.filters;

import com.portfolio.main.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.config.security.jwt.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = TokenUtil.getTokenFromRequest(request);

        if (token != null && !token.isEmpty()) {
            final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}
