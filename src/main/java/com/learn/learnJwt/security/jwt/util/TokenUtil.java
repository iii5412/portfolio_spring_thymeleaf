package com.learn.learnJwt.security.jwt.util;

import com.learn.learnJwt.auth.JwtResponse;
import com.learn.learnJwt.security.jwt.JwtAuthenticationToken;
import com.learn.learnJwt.security.jwt.provider.JwtProvider;
import com.learn.learnJwt.util.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

import static com.learn.learnJwt.util.CookieUtils.getCookie;

public class TokenUtil {
    public static String getTokenFromRequest(HttpServletRequest request) {
        return getCookie(request, JwtAuthenticationToken.TOKEN_NAME);
    }

    public static void setTokenToResponse(HttpServletResponse response, JwtResponse jwtRespons) {
        final Cookie cookie = new Cookie(JwtAuthenticationToken.TOKEN_NAME, jwtRespons.getToken());
        cookie.setHttpOnly(true); // JavaScript에서의 접근 방지;
//        cookie.setSecure(true); //HTTPS만 허용
        cookie.setPath("/");
        cookie.setMaxAge((int) (JwtProvider.validityInMilliseconds / 1000));

        CookieUtils.setCookie(response, cookie);
    }
}
