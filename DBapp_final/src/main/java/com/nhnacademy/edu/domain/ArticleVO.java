package com.nhnacademy.edu.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ArticleVO {
    int id;
    String title;
    String content;
    Timestamp date;
}
