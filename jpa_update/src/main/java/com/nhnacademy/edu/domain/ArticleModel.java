package com.nhnacademy.edu.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ArticleModel {

    @NotBlank
    @Size(max = 100)
    String title;

    @NotBlank
    String content;

    MultipartFile[] multipartFiles;
}


