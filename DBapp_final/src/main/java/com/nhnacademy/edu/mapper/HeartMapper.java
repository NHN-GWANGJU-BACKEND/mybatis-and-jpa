package com.nhnacademy.edu.mapper;

import com.nhnacademy.edu.domain.Heart;
import com.nhnacademy.edu.domain.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeartMapper {
    Heart findByIds(@Param("articleId") int articleId, @Param("userId") String userId);

    List<Post> findUserHeartPost(@Param("limit") int limit, @Param("offset") int offset, @Param("userId") String userId);

    int getTotalCount(@Param("userId") String userId);

    int insertHeart(@Param("articleId") int articleId, @Param("userId") String userId);

    int deleteHeart(@Param("articleId") int articleId, @Param("userId") String userId);
}
