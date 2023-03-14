package com.nhnacademy.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageFile {
    int articleId;
    String originalFileName;
    String saveFileName;
    String saveDirectory;
}
