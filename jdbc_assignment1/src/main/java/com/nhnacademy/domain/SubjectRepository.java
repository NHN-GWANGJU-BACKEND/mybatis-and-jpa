package com.nhnacademy.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SubjectRepository {
    Subject findByID(long id);
    List<Subject> findAll();
}
