package com.nhnacademy.repository;

import com.nhnacademy.domain.Course;
import com.nhnacademy.domain.CourseRepository;
import com.nhnacademy.domain.SubjectRepository;
import com.nhnacademy.domain.TeacherRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcTemplateCourseRepository implements CourseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public JdbcTemplateCourseRepository(DataSource dataSource, TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query("select id, teacher_id, subject_id, created_at from JdbcCourses",
                ((rs, rowNum) -> new Course(
                        rs.getLong("id"),
                        teacherRepository.findById(rs.getLong("teacher_id")),
                        subjectRepository.findByID(rs.getLong("subject_id")),
                        rs.getTimestamp("created_at")
                )));
    }

    @Override
    public Course findById(long id) {
        return jdbcTemplate
                .queryForObject("select id, teacher_id, subject_id, created_at from JdbcCourses where id = ?",
                        ((rs, rowNum) -> new Course(
                                rs.getLong("id"),
                                teacherRepository.findById(rs.getInt("teacher_id")),
                                subjectRepository.findByID(rs.getInt("subject_id")),
                                rs.getTimestamp("created_at")
                        )), id);
    }

    @Override
    public int insert(Course course) {
        return jdbcTemplate.update(
                "INSERT INTO JdbcCourses(teacher_id, subject_id,created_at) VALUES (?,?,?)",
                course.getTeacher().getId(),
                course.getSubject().getId(),
                course.getCreateAt()
        );
    }

    @Override
    public int modify(Course course) {
        return jdbcTemplate.update(
                "UPDATE JdbcCourses Set teacher_id=?, subject_id=? where id = ?",
                course.getTeacher().getId(),
                course.getSubject().getId(),
                course.getId()
        );
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update("DELETE FROM JdbcCourses WHERE id=?", id);
    }


}
