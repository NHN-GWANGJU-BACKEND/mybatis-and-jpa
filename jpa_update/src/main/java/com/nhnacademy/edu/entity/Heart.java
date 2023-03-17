package com.nhnacademy.edu.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Heart")
public class Heart {
    @EmbeddedId
    private Pk pk;

    @MapsId("articleId")
    @OneToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @MapsId("userId")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "article_id")
        private int articleId;

        @Column(name = "user_id")
        private String userId;
    }
}
