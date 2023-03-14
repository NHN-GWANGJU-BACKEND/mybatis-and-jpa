package com.nhnacademy.repository;

import com.nhnacademy.domain.Teacher;
import com.nhnacademy.domain.TeacherRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Repository
public class JdbcTemplateTeacherRepository implements TeacherRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateTeacherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher findById(long id) {

        return jdbcTemplate.queryForObject("select id, name, created_at from JdbcTeachers where id = ?",
                ((rs, rowNum) -> new Teacher(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getTimestamp(3))),
                id);
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query("select id, name, created_at from JdbcTeachers",
                (rs, rowNum) -> new Teacher(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getTimestamp(3)));
    }
}
