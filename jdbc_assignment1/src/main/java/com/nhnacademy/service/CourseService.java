package com.nhnacademy.service;

import com.nhnacademy.domain.Course;
import com.nhnacademy.domain.Subject;
import com.nhnacademy.domain.Teacher;

import java.util.List;

public interface CourseService {
    List<Course> getCourses();
    Course getCourseById(long id);
    int save(Course course);
    int modify(Course course);
    int delete(long id);
}
