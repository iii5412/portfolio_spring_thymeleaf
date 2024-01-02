package com.portfolio.main.presentation.rest.account.user;

import com.portfolio.main.application.login.dto.UserDto;
import com.portfolio.main.application.login.service.UserQueryService;
import com.portfolio.main.domain.model.account.User;
import com.portfolio.main.infrastructure.config.security.service.MyUserDetailsService;
import com.portfolio.main.presentation.rest.account.user.response.UserResponse;
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

    private final UserQueryService userQueryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        final List<UserDto> allUser = userQueryService.findAll();
        final List<UserResponse> userResponses = allUser.stream()
                .map(UserResponse::new)
                .toList();

        return ResponseEntity.ok(userResponses);
    }


}
