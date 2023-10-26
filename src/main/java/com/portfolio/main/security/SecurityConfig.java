package com.portfolio.main.security;

import com.portfolio.main.security.jwt.filters.JwtAuthenticationFilter;
import com.portfolio.main.security.jwt.filters.JwtAuthorizationFilter;
import com.portfolio.main.security.jwt.filters.JwtTokenRefreshFilter;
import com.portfolio.main.security.jwt.provider.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private JwtAuthenticationProvider jwtAuthenticationProvider;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        final String[] permitAllUrls = {"/", "/account/login", "/account/signup", "/loginPage", "/js/**", "/css/**", "/images/**"};
//
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(permitAllUrls).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(),
//                        UsernamePasswordAuthenticationFilter.class
//                )
//                .addFilterAfter(
//                        new JwtTokenRefreshFilter(),
//                        UsernamePasswordAuthenticationFilter.class
//                )
//                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(jwtAuthenticationProvider);
//    }
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers("/", "/account/login", "/account/signup", "/loginPage", "/js/**", "/css/**", "/images/**").permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(
                        new JwtTokenRefreshFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> jwtAuthenticationProvider.authenticate(authentication);
    }
}
