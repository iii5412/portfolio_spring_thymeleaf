package com.portfolio.main.config.security.jwt.filters;

import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.config.security.jwt.util.TokenUtil;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
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
    private final MyUserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager
            , MyUserDetailsService userDetailsService
            , String secretKeyString) {
//        super(authenticationManager, ((request, response, authException) -> {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.sendRedirect("/loginPage");
//        }));
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secretKeyString = secretKeyString;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, ExpiredJwtException {
        try {

            if(request.getRequestURI().equals("/loginPage")){
                chain.doFilter(request, response);
                return;
            }

            final String tokenFromRequest = TokenUtil.getTokenFromRequest(request);

            if (!StringUtils.hasText(tokenFromRequest)) {
                chain.doFilter(request, response);
                return;
            }

            Authentication authentication = getAuthentication(tokenFromRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            this.getAuthenticationEntryPoint().commence(request, response, new AuthenticationServiceException("Token expired", e));
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
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            }
        }
        return null;
    }
}
