package com.nhnacademy.repository;

import com.nhnacademy.domain.User;
import com.nhnacademy.domain.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findByUserName(String userName) {
        return jdbcTemplate
                .queryForObject("select username, password from JdbcUsers where username = ?",
                        ((rs, rowNum) -> new User(rs.getString("userName"), rs.getString("password"))),
                        userName);
    }
}
