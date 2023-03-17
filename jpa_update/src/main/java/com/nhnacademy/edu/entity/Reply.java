package com.nhnacademy.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reply")
public class Reply {
    @Id
    @Column(name = "reply_id")
    private Integer replyId;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article articleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


