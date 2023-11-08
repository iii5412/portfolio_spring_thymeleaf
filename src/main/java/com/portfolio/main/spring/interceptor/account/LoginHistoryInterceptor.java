package com.portfolio.main.spring.interceptor.account;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.domain.UserLoginHistory;
import com.portfolio.main.account.login.repository.UserLoginHistoryRepository;
import com.portfolio.main.account.login.service.LoginActionType;
import com.portfolio.main.config.security.MyUserDetails;
import com.portfolio.main.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.config.security.jwt.provider.JwtAuthenticationProvider;
import com.portfolio.main.config.security.jwt.util.TokenUtil;
import com.portfolio.main.util.RequestUtils;
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

    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private UserLoginHistoryRepository userLoginHistoryRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        final String requestURI = request.getRequestURI();
        String token = getToekn(request);

        final User user = getUser(token);

        if ("/account/login".equals(requestURI)) {
            saveLoginHistory(request, user);
        } else {
            saveLogoutHistory(request, user);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String getToekn(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();

        if("/account/login".equals(requestURI)){
            //최초 로그인시에는 응답이 나가기 전 까지 cookie에 토큰이 없으니 로그인시 Controller에서 담아준 token사용.
            return (String) request.getAttribute("token");
        } else {
            return TokenUtil.getTokenFromRequest(request);
        }
    }

    private User getUser(String token) {
        final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
        final JwtAuthenticationToken authenticate = (JwtAuthenticationToken) jwtAuthenticationProvider.authenticate(jwtAuthenticationToken);
        final MyUserDetails userDetails = (MyUserDetails) authenticate.getPrincipal();
        return userDetails.getUser();
    }

    private void saveLoginHistory(HttpServletRequest request, User user) {
        final UserLoginHistory userLoginHistory = new UserLoginHistory(user.getLoginId(), LoginActionType.LOGIN, RequestUtils.getClientIP(request), RequestUtils.getClientDeviceInfo(request));
        userLoginHistoryRepository.save(userLoginHistory);
    }

    private void saveLogoutHistory(HttpServletRequest request, User user) {
        final UserLoginHistory userLoginHistory = new UserLoginHistory(user.getLoginId(), LoginActionType.LOGOUT, RequestUtils.getClientIP(request), RequestUtils.getClientDeviceInfo(request));
        userLoginHistoryRepository.save(userLoginHistory);
    }
}
