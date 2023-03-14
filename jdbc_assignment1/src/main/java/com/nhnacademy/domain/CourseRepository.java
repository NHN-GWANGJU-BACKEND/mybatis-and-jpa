package com.nhnacademy.domain;

import java.util.List;

public interface CourseRepository {
    List<Course> findAll();
    Course findById(long id);

    int insert(Course course);

    int modify(Course course);

    int delete(long id);
}
