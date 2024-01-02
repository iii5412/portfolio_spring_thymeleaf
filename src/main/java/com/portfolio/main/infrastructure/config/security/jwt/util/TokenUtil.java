package com.portfolio.main.infrastructure.config.security.jwt.util;

import com.portfolio.main.presentation.rest.account.login.response.JwtResponse;
import com.portfolio.main.infrastructure.config.security.jwt.JwtAuthenticationToken;
import com.portfolio.main.infrastructure.config.security.jwt.provider.JwtProvider;
import com.portfolio.main.common.util.CookieUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;

import static com.portfolio.main.common.util.CookieUtils.getCookie;

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

    public static byte[] getSecretKeyStringToEncodingUTF8Bytes(String secretKeyString) {
        return secretKeyString.getBytes(StandardCharsets.UTF_8);
    }
}
