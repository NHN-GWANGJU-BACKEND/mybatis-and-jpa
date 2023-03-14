package com.nhnacademy.domain;

import java.util.Date;

public class Subject {
    private final long id;
    private final String name;
    private final Date createAt;

    public Subject(long id, String name, Date createAt) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreateAt() {
        return createAt;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
