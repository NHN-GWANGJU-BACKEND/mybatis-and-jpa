package com.nhnacademy.edu.entity;

import com.nhnacademy.edu.config.RootConfig;
import com.nhnacademy.edu.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class ResidentTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void test() {
        Resident resident = entityManager.find(Resident.class, 1);
        assertThat(resident.getName()).isEqualTo("남길동");
    }
}