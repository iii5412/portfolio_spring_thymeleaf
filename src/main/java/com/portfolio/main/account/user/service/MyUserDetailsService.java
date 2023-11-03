package com.portfolio.main.account.user.service;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.login.exception.InvalidLoginId;
import com.portfolio.main.account.login.exception.InvalidLoginPassword;
import com.portfolio.main.account.login.exception.InvalidUserId;
import com.portfolio.main.account.user.repository.UserRepository;
import com.portfolio.main.config.security.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws InvalidLoginId {
        final User findUser = userRepository.findByLoginId(loginId).orElseThrow(InvalidLoginId::new);
        return new MyUserDetails(findUser);
    }

    public User findUserByLoginId(String loginId) throws InvalidLoginId {
        return userRepository.findByLoginId(loginId).orElseThrow(InvalidLoginId::new);
    }

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(InvalidUserId::new);
    }

    public User validateUser(String loginId, String loginPw) throws InvalidLoginId, InvalidLoginPassword {
        final User user = userRepository.findByLoginId(loginId)
                .orElseThrow(InvalidLoginId::new);

        if (!checkPassword(loginPw, user.getLoginPw()))
            throw new InvalidLoginPassword();

        return user;
    }

    private boolean checkPassword(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }
}
