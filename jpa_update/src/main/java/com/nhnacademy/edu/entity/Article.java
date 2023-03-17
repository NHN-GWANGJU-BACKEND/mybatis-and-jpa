package com.nhnacademy.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Article")
public class Article {
    @Id
    @Column(name = "article_id")
    Integer articleId;

    private String title;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name="isDelete")
    private String isDelete;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    private int replyCount;
}
