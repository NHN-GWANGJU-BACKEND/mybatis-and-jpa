package com.nhnacademy.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TeacherRepository {
    Teacher findById(long id);
    List<Teacher> findAll();
}
