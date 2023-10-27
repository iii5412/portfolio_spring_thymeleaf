package com.portfolio.main.account.login.repository;

import com.portfolio.main.account.domain.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
}
