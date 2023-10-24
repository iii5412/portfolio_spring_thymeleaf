package com.learn.learnJwt.domain;

import java.util.List;

public class User {
    private Long id;
    private String loginId;
    private String username;
    private String password;
    private List<String> roles;

    public User(Long id, String loginId, String username, String password, List<String> roles) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getLoginId() {
        return loginId;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
