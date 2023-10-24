package com.learn.learnJwt.view;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails != null)
            return "/";

        return "login/loginPage";
    }

}
