package com.nhnacademy.service;

import com.nhnacademy.domain.Teacher;
import com.nhnacademy.domain.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcTemplateTeacherService implements TeacherService {
    private final TeacherRepository teacherRepository;

    public JdbcTemplateTeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(long id) {
        return teacherRepository.findById(id);
    }
}
