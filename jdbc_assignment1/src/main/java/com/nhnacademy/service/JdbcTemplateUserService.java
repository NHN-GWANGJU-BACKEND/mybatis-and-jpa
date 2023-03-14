package com.nhnacademy.service;

import com.nhnacademy.domain.User;
import com.nhnacademy.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class JdbcTemplateUserService implements UserService {
    private final UserRepository userRepository;

    public JdbcTemplateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean match(String userId, String password) {
        User user = userRepository.findByUserName(userId);
        return user.getUserName().equals(userId) && user.getPassword().equals(password);
    }
}
