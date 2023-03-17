package com.nhnacademy.edu.repository.user;

import com.nhnacademy.edu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
