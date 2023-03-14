package com.nhnacademy.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleDTO {
    int articleId;
    String title;
    String content;
    Timestamp createdAt;
    boolean delete;
    Timestamp updateAt;
    int replyCount;
}
