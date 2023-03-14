package com.nhnacademy.repository;

import com.nhnacademy.domain.Subject;
import com.nhnacademy.domain.SubjectRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcTemplateSubjectRepository implements SubjectRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateSubjectRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Subject findByID(long id) {
        return jdbcTemplate.queryForObject("select id, name, created_at from JdbcSubjects where id = ?",
                ((rs, rowNum) -> new Subject(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at"))),
                id);
    }

    @Override
    public List<Subject> findAll() {
        return jdbcTemplate.query("select id, name, created_at from JdbcSubjects",
                (rs, rowNum) -> new Subject(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at")));
    }
}
