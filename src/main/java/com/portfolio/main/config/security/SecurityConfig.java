package com.portfolio.main.config.security;

import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.config.security.jwt.filters.JwtAuthenticationFilter;
import com.portfolio.main.config.security.jwt.filters.JwtAuthorizationFilter;
import com.portfolio.main.config.security.jwt.filters.JwtTokenRefreshFilter;
import com.portfolio.main.config.security.jwt.provider.JwtAuthenticationProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final MyUserDetailsService userDetailsService;
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
        final String[] permitAllUrls = {"/", "/error/**", "/account/login", "/account/signup", "/signup", "/loginPage", "/menu"};

        http
                .csrf().disable()
                .headers().frameOptions().sameOrigin() //Iframe 호출을 위해 추가
                .and()
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers(permitAllUrls).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, secretKeyString))
                .addFilterAfter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, secretKeyString), JwtAuthenticationFilter.class)
                .addFilterAfter(
                        new JwtTokenRefreshFilter(),
                        JwtAuthorizationFilter.class
                )
                .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }).authenticationEntryPoint((request,response,authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
