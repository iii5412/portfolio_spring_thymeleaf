package com.learn.learnJwt.user;

import com.learn.learnJwt.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {
    public User findByUserId(String userId) {
        if("iii5412".equals(userId)) {
            final List<String> adminRole = Arrays.asList("admin", "user");
            return new User(1L, userId, "최민철", "1234", adminRole);
        }
        return null;
    }
}
