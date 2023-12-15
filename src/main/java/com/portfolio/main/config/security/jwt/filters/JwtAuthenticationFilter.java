package com.portfolio.main.config.security.jwt.filters;

import com.portfolio.main.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.config.security.jwt.util.TokenUtil;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.portfolio.main.config.security.SecurityConfig.PERMIT_ALL_URLS;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String secretKeyString;

    public JwtAuthenticationFilter(String secretKeyString) {
        this.secretKeyString = secretKeyString;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = TokenUtil.getTokenFromRequest(request);

        if (token != null && !token.isEmpty()) {
            try {
                validExpiredToken(token);
                final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
            } catch (ExpiredJwtException e) {
                if (!Arrays.asList(PERMIT_ALL_URLS).contains(request.getRequestURI())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    chain.doFilter(request, response);
                    return;
                } else {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private void validExpiredToken(String token) {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(TokenUtil.getSecretKeyStringToEncodingUTF8Bytes(secretKeyString)).build();
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
    }

}
