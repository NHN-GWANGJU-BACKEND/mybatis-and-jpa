package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.Heart;
import com.nhnacademy.edu.domain.Post;

import java.util.List;

public interface HeartService {
    Heart findHeart(int articleId, String userId);

    List<Post> findUserHeartPost(int page, int limit, String userId);

    int getTotalCount(String userId);

    int insertHeart(int articleId, String userId);

    int deleteHeart(int articleId, String userId);
}
