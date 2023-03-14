package com.nhnacademy.service;

import com.nhnacademy.domain.Subject;
import com.nhnacademy.domain.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcTemplateSubjectService implements SubjectService {
    private final SubjectRepository subjectRepository;

    public JdbcTemplateSubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(long id) {
        return subjectRepository.findByID(id);
    }
}
