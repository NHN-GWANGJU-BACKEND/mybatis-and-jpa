package com.nhnacademy.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
    int id;
    ArticleDTO articleDTO;
    User createUser;
    User modifyUser;
}


