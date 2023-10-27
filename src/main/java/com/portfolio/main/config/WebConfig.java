package com.portfolio.main.config;

import com.portfolio.main.interceptor.account.LoginHistoryInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private LoginHistoryInterceptor loginHistoryInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHistoryInterceptor)
                .addPathPatterns("/login", "/louout");
    }
}
