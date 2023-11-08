package com.portfolio.main.controller.view.login;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginPage")
    public String loginPage(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails != null)
            return "/";

        return "login/loginPage";
    }

    @GetMapping("/signup")
    public String signUpPage(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails != null)
            return "/";

        return "/login/signup";
    }
}
