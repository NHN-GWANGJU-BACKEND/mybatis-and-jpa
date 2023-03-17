package com.nhnacademy.edu.service;

import com.nhnacademy.edu.entity.User;

import java.util.List;

public interface UserService {
    boolean match(String userId, String password);

    User findById(String id);

    List<User> findAll();
}
