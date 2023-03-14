package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.Heart;
import com.nhnacademy.edu.domain.Post;
import com.nhnacademy.edu.mapper.HeartMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBatisHeartService implements HeartService {
    private final HeartMapper heartMapper;

    public MyBatisHeartService(HeartMapper heartMapper) {
        this.heartMapper = heartMapper;
    }

    @Override
    public Heart findHeart(int articleId, String userId) {
        return heartMapper.findByIds(articleId, userId);
    }

    @Override
    public List<Post> findUserHeartPost(int page, int limit, String userId) {
        return heartMapper.findUserHeartPost(limit, (page - 1) * limit, userId);
    }

    @Override
    public int getTotalCount(String userId) {
        return heartMapper.getTotalCount(userId);
    }

    @Override
    public int insertHeart(int articleId, String userId) {
        return heartMapper.insertHeart(articleId, userId);
    }

    @Override
    public int deleteHeart(int articleId, String userId) {
        return heartMapper.deleteHeart(articleId, userId);
    }
}
