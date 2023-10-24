package com.learn.learnJwt.user;

import com.learn.learnJwt.domain.User;
import com.learn.learnJwt.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        final User findUser = userRepository.findByUserId(loginId);
        return new MyUserDetails(findUser);
    }

    public User validateUser(String loginId, String password) {
        final User user = userRepository.findByUserId(loginId);
        if (user != null){
            String encodedUserPassword = passwordEncoder.encode(user.getPassword());
            if(passwordEncoder.matches(password, encodedUserPassword)) {
                return user;
            }
        }
        return null;
    }
}
