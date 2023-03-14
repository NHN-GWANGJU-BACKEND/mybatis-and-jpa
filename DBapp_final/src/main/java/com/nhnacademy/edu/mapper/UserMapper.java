package com.nhnacademy.edu.mapper;

import com.nhnacademy.edu.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findById(String id);

    List<User> findAll();
}
