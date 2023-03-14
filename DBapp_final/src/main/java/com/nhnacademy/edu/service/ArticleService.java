package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.ArticleVO;
import com.nhnacademy.edu.domain.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleService {
    List<Post> findPostListUser(int page, int limit, String keyword);

    List<Post> findPostListAdmin(int page, int limit, String keyword);

    Post findPostByArticleId(int articleId);

    int getAdminTotalCount(String keyword);

    int getUserTotalCount(String keyword);

    int insertArticle(ArticleVO articleVo);

    int insertPost(int articleId, String userId);

    int updateArticle(ArticleVO articleVo);

    int updateReplyCountPlus(long articleId);

    int updateReplyCountMinus(long articleId);

    int updatePost(int postId, String userId);

    int deletePost(long articleId);

    int restorePost(@Param("articleId") long articleId);

}
