package com.portfolio.main.config.security.jwt.filters;

import com.portfolio.main.config.security.jwt.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secretKeyString;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secretKeyString) {
        super(authenticationManager);
        this.secretKeyString = secretKeyString;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String tokenFromRequest = TokenUtil.getTokenFromRequest(request);

        if (!StringUtils.hasText(tokenFromRequest)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getAuthentication(tokenFromRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        if (token != null) {
            final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(TokenUtil.getSecretKeyStringToEncodingUTF8Bytes(secretKeyString)).build();
            final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            final String user = claimsJws.getBody().getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }
        return null;
    }
}
