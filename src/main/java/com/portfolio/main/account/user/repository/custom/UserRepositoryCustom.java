package com.portfolio.main.account.user.repository.custom;

import com.portfolio.main.account.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByLoginId(String LoginId);
    List<User> findAll();
}
