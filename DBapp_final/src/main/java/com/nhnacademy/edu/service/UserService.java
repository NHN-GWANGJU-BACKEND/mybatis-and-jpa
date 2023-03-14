package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.User;

import java.util.List;

public interface UserService {
    boolean match(String userId, String password);

    User findById(String id);

    List<User> findAll();
}
