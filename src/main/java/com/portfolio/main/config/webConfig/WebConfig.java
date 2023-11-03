package com.portfolio.main.config.webConfig;

import com.portfolio.main.spring.interceptor.account.LoginHistoryInterceptor;
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
                .addPathPatterns("/account/login", "/account/logout");
    }
}
