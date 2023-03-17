package com.nhnacademy.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Board")
public class Board {
    @Id
    @Column(name = "board_id")
    private Integer boardId;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "createUser_id")
    private User createUser;

    @ManyToOne
    @JoinColumn(name = "modifyUser_id")
    private User modifyUser;

    @PrePersist
    public void prePersist() {
        User user = new User("defaultModifier", "!ksdh/324vx@!# klsdzxj@#$#@ %%#@", "없음", 8);
        this.modifyUser = this.modifyUser == null ? user : this.modifyUser;
    }
}


