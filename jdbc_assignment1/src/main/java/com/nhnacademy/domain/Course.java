package com.nhnacademy.domain;

import java.util.Date;

public class Course {
    private final long id;
    private Teacher teacher;
    private Subject subject;
    private final Date createAt;

    public Course(long id, Teacher teacher, Subject subject, Date createAt) {
        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
        this.createAt = createAt;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", subject=" + subject +
                ", createAt=" + createAt +
                '}';
    }
}
