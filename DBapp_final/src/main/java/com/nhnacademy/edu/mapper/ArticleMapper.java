package com.nhnacademy.edu.mapper;

import com.nhnacademy.edu.domain.ArticleVO;
import com.nhnacademy.edu.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Post> findPostListUser(@Param("limit") int limit, @Param("offset") int offset, @Param("keyword") String keyword);

    List<Post> findPostListAdmin(@Param("limit") int limit, @Param("offset") int offset, @Param("keyword") String keyword);

    Post findPostByArticleId(@Param("id") long id);

    int getAdminTotalCount(@Param("keyword") String keyword);

    int getUserTotalCount(@Param("keyword") String keyword);

    int updateReplyCountPlus(@Param("articleId") long articleId);

    int updateReplyCountMinus(@Param("articleId") long articleId);

    int insertArticle(ArticleVO articleVo);

    int insertPost(@Param("articleId") long articleId, @Param("userId") String userId);

    int updateArticle(ArticleVO articleVo);

    int updatePost(@Param("postId") int postId, @Param("userId") String userId);

    int deletePost(@Param("articleId") long articleId);

    int restorePost(@Param("articleId") long articleId);


}
