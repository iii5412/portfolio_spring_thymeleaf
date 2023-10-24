package com.learn.learnJwt.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public static final String TOKEN_NAME = "authToken";
    private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public JwtAuthenticationToken(UserDetails userDetails) {
        super(userDetails, null, userDetails.getAuthorities());
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }
}
