package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.User;
import com.nhnacademy.edu.exception.NotExistUserId;
import com.nhnacademy.edu.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MybatisUserService implements UserService {
    private final UserMapper userMapper;

    public MybatisUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean match(String userId, String password) {
        Optional<User> user = userMapper.findById(userId);

        if (user.isPresent()) {
            return user.get().getUserId().equals(userId) && user.get().getPassword().equals(password);
        }

        throw new NotExistUserId();
    }

    @Override
    public User findById(String id) {
        Optional<User> user = userMapper.findById(id);

        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
