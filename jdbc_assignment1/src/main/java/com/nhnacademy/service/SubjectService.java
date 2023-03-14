package com.nhnacademy.service;

import com.nhnacademy.domain.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getSubjects();
    Subject getSubjectById(long id);
}
