package com.portfolio.main.config.security;

import com.portfolio.main.config.security.jwt.filters.JwtAuthenticationFilter;
import com.portfolio.main.config.security.jwt.filters.JwtAuthorizationFilter;
import com.portfolio.main.config.security.jwt.filters.JwtTokenRefreshFilter;
import com.portfolio.main.config.security.jwt.provider.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Value("${jwt.secretKey}")
    private String secretKeyString;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final String[] permitAllUrls = {"/", "/error", "/account/login", "/account/signup", "/signup", "/loginPage"};
        final String[] permitAllResources = {"/js/**", "/css/**", "/favicon.ico"};

        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers(permitAllUrls).permitAll()
                            .requestMatchers(permitAllResources).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), secretKeyString))
                .addFilterAfter(
                        new JwtTokenRefreshFilter(),
                        JwtAuthorizationFilter.class
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> jwtAuthenticationProvider.authenticate(authentication);
    }
}
