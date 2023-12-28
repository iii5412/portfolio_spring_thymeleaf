package com.portfolio.main.infrastructure.config.security.jwt.filters;

import com.portfolio.main.infrastructure.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.infrastructure.config.security.jwt.util.TokenUtil;
import com.portfolio.main.infrastructure.config.security.SecurityConfig;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                if (!Arrays.asList(SecurityConfig.PERMIT_ALL_URLS).contains(request.getRequestURI())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    chain.doFilter(request, response);
                    return;
                } else {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(request, response);
                    return;
                }
            }
        } else {
            final Authentication guestAuthentication = createGuestAuthentication();
            SecurityContextHolder.getContext().setAuthentication(guestAuthentication);
        }

        chain.doFilter(request, response);
    }

    private void validExpiredToken(String token) {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(TokenUtil.getSecretKeyStringToEncodingUTF8Bytes(secretKeyString)).build();
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
    }

    private Authentication createGuestAuthentication() {
        final List<GrantedAuthority> guestAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST"));
        return new AnonymousAuthenticationToken("guestUserKey", "guest", guestAuthorities);
    }

}
