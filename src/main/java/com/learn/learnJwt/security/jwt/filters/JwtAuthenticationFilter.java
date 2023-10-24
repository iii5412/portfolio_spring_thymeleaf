package com.learn.learnJwt.security.jwt.filters;

import com.learn.learnJwt.auth.JwtResponse;
import com.learn.learnJwt.security.jwt.JwtAuthenticationToken;
import com.learn.learnJwt.security.jwt.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = TokenUtil.getTokenFromRequest(request);

        if (token != null) {
            final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}
