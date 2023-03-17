package com.nhnacademy.edu.repository;

import com.nhnacademy.edu.config.RootConfig;
import com.nhnacademy.edu.config.WebConfig;
import com.nhnacademy.edu.entity.User;
import com.nhnacademy.edu.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUser(){
        Optional<User> admin = userRepository.findById("admin");

        assertThat(admin.isPresent()).isTrue();
        assertThat(admin.get().getUserName()).isEqualTo("admin");
    }

    @Test
    void getAllUser(){
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(4);
    }

}