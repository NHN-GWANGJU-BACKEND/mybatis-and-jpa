package com.nhnacademy.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ImageFile")
public class ImageFile {
    @Id
    @Column(name = "image_file_id")
    Integer imageFileId;

    @JoinColumn(name = "article_id")
    @ManyToOne
    Article article;

    String originalFileName;

    String saveFileName;

    String saveDirectory;
}
