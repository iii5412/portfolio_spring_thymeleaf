package com.portfolio.main.controller.api.account.user;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.user.service.MyUserDetailsService;
import com.portfolio.main.controller.api.account.user.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final MyUserDetailsService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        final List<User> allUser = userService.findAllUser();
        final List<UserResponse> userResponses = allUser.stream()
                .map(UserResponse::new)
                .toList();

        return ResponseEntity.ok(userResponses);
    }


}
