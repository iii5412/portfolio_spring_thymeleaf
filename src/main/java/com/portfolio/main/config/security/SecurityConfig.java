package com.portfolio.main.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.config.security.jwt.filters.JwtAuthenticationFilter;
import com.portfolio.main.config.security.jwt.filters.JwtAuthorizationFilter;
import com.portfolio.main.config.security.jwt.filters.JwtTokenRefreshFilter;
import com.portfolio.main.config.security.jwt.provider.JwtAuthenticationProvider;
import com.portfolio.main.controller.api.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final MyUserDetailsService userDetailsService;

    public static final String[] PERMIT_ALL_URLS = {"/", "/error/**", "/account/login", "/account/signup", "/signup", "/loginPage", "/menu"};

    @Autowired
    public SecurityConfig(
            JwtAuthenticationProvider jwtAuthenticationProvider
            , MyUserDetailsService userDetailsService
    ) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.userDetailsService = userDetailsService;
    }

    @Value("${jwt.secretKey}")
    private String secretKeyString;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().sameOrigin() //Iframe 호출을 위해 추가
                .and()
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers(PERMIT_ALL_URLS).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, secretKeyString))
                .addFilterAfter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, secretKeyString), JwtAuthenticationFilter.class)
                .addFilterAfter(
                        new JwtTokenRefreshFilter(),
                        JwtAuthorizationFilter.class
                )
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    final ErrorResponse errorResponse = ErrorResponse.builder()
                            .code(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED))
                            .message("인증 실패 : " + authException.getMessage())
                            .build();

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    final ErrorResponse errorResponse = ErrorResponse.builder()
                            .code(String.valueOf(HttpServletResponse.SC_FORBIDDEN))
                            .message("인가 실패 : " + accessDeniedException.getMessage())
                            .build();

                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
                })
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return jwtAuthenticationProvider::authenticate;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        final String[] permitAllResources = {"/js/**", "/css/**", "/favicon.ico"};
        return (web) -> web.ignoring().requestMatchers(permitAllResources);
    }
}
