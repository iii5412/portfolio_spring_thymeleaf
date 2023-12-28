package com.portfolio.main.infrastructure.repository.custom.user;

import com.portfolio.main.domain.model.account.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByLoginId(String LoginId);
    List<User> findAll();
}
