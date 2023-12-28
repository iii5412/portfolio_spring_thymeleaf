package com.portfolio.main.presentation.web.login;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class LoginController {
    @GetMapping("/loginPage")
    public String loginPage(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws IOException {
        if(userDetails != null)
            response.sendRedirect("/");

        return "login/loginPage";
    }

    @GetMapping("/signup")
    public String signUpPage(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws IOException {
        if(userDetails != null)
            response.sendRedirect("/");

        return "/login/signup";
    }
}
