package com.portfolio.main.account.user.service;

import com.portfolio.main.account.domain.User;
import com.portfolio.main.account.exception.InvalidLoginId;
import com.portfolio.main.account.exception.InvalidLoginPassword;
import com.portfolio.main.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws InvalidLoginId {
        final User findUser = userRepository.findByLoginId(loginId).orElseThrow(InvalidLoginId::new);
        return new MyUserDetails(findUser);
    }

    public User validateUser(String loginId, String password) throws InvalidLoginId, InvalidLoginPassword {
        final User user = userRepository.findByLoginId(loginId)
                .orElseThrow(InvalidLoginId::new);

        String encodedUserPassword = passwordEncoder.encode(user.getLoginPw());
        if (!passwordEncoder.matches(password, encodedUserPassword))
            throw new InvalidLoginPassword();

        return user;
    }
}
