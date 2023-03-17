package com.nhnacademy.edu.service;

import com.nhnacademy.edu.entity.Board;
import com.nhnacademy.edu.entity.Heart;

import java.util.List;

public interface HeartService {
    Heart findHeart(int articleId, String userId);

    Heart insertHeart(int articleId, String userId);

    int deleteHeart(int articleId, String userId);
}
