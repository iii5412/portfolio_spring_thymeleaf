package com.portfolio.main.presentation.rest;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.login.service.UserQueryService;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.domain.service.account.UserService;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
import com.portfolio.main.infrastructure.config.security.jwt.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TestAuth {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;

    public static final String USER_ADMIN = "admin";
    public static final String USER_USER = "user";
    public static final String USER_GUEST = "guest";

    public String setUserAdminAndGetToken() {
        return setUserAuthentication(USER_ADMIN);
    }

    public String setUserUserAndGetToken() {
        return setUserAuthentication(USER_USER);
    }

    public void setUserGuest() {
        setUserAuthentication(USER_GUEST);
    }

    private String setUserAuthentication(String type) {
        String loginId = type;
        String loginPw = "";
        String token = "";

        if(type.equals(USER_ADMIN)) {
            loginPw = "6212";
        } else if(type.equals(USER_USER)) {
            loginPw = "1234";
        }

        if(!type.equals(USER_GUEST)) {
            userService.validateLoginCredentials(loginId, loginPw);

            final UserDto userDto = userQueryService.findByLoginId(loginId);


            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
            final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtProvider.createToken(userDto);
        } else {
            final List<GrantedAuthority> guestAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST"));
            final AnonymousAuthenticationToken guestAuthentication = new AnonymousAuthenticationToken("guestUserKey", "guest", guestAuthorities);
            SecurityContextHolder.getContext().setAuthentication(guestAuthentication);
        }

        return token;
    }

}
