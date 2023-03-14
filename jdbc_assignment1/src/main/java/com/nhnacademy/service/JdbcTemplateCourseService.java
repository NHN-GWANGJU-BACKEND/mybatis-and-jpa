package com.nhnacademy.service;

import com.nhnacademy.domain.Course;
import com.nhnacademy.domain.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcTemplateCourseService implements CourseService {
    private final CourseRepository courseRepository;

    public JdbcTemplateCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    public int save(Course course) {
        return courseRepository.insert(course);
    }

    @Override
    public int modify(Course course) {
        return courseRepository.modify(course);
    }

    @Override
    public int delete(long id) { return courseRepository.delete(id); }


}
