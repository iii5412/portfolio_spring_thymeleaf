package com.portfolio.main.infrastructure.config.security.jwt.filters;

import com.portfolio.main.infrastructure.config.security.jwt.util.TokenUtil;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secretKeyString;
    private final MyUserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager
            , MyUserDetailsService userDetailsService
            , String secretKeyString) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secretKeyString = secretKeyString;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, ExpiredJwtException {
        try {
            final String tokenFromRequest = TokenUtil.getTokenFromRequest(request);

            if (!StringUtils.hasText(tokenFromRequest)) {
                chain.doFilter(request, response);
                return;
            }

            Authentication authentication = getAuthentication(tokenFromRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        if (token != null) {
            final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(TokenUtil.getSecretKeyStringToEncodingUTF8Bytes(secretKeyString)).build();
            final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            final String loginId = claimsJws.getBody().getSubject();

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);


            if (loginId != null) {
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
        }
        return null;
    }
}
