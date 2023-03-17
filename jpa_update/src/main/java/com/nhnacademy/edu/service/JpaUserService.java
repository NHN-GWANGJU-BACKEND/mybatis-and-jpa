package com.nhnacademy.edu.service;

import com.nhnacademy.edu.entity.User;
import com.nhnacademy.edu.exception.NotExistUserId;
import com.nhnacademy.edu.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class JpaUserService implements UserService {
    private final UserRepository userRepository;

    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean match(String userId, String password) {
        log.info("parsing");
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get().getUserId().equals(userId) && user.get().getPassword().equals(password);
        }

        throw new NotExistUserId();
    }

    @Override
    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
