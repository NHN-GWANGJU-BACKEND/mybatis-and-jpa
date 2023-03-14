package com.nhnacademy.domain;

public interface UserRepository {
    User findByUserName(String userName);
}
