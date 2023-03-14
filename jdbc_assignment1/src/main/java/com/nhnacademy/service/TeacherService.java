package com.nhnacademy.service;

import com.nhnacademy.domain.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachers();
    Teacher getTeacherById(long id);
}
