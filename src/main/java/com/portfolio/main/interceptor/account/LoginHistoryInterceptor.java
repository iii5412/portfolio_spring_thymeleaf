package com.portfolio.main.interceptor.account;

import com.portfolio.main.account.login.repository.UserLoginHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@AllArgsConstructor
@Slf4j
public class LoginHistoryInterceptor implements HandlerInterceptor {

    private UserLoginHistoryRepository userLoginHistoryRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        final String requestURI = request.getRequestURI();

        if ("/login".equals(requestURI)) {
            log.info("login 하였습니다.");
        } else if ("/logout".equals(requestURI)) {
            log.info("logout 하였습니다.");
        }
    }


}
