package com.nhnacademy.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    int replyId;
    User user;
    int articleId;
    String content;
    Timestamp createdAt;
    boolean delete;
}


