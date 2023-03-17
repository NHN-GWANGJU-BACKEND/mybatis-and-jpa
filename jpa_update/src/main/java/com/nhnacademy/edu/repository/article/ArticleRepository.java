package com.nhnacademy.edu.repository.article;

import com.nhnacademy.edu.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Transactional
    @Modifying
    @Query("update Article a set a.title=:title, a.content = :content, a.updateAt =:updateAt where a.articleId = :articleId")
    int updateArticle(@Param("title") String title, @Param("content") String content, @Param("updateAt") Timestamp updateAt,
                      @Param("articleId") int articleId);

    @Modifying
    @Transactional
    @Query("update Article a set a.replyCount = a.replyCount+1 where a.articleId = :articleId")
    int updateReplyCountPlus(@Param("articleId") int articleId);

    @Transactional
    @Modifying
    @Query("update Article a set a.replyCount = a.replyCount-1 where a.articleId = :articleId")
    int updateReplyCountMinus(@Param("articleId") int articleId);

    @Transactional
    @Modifying
    @Query("Update Article a set a.isDelete = 'Y' where a.articleId = :articleId")
    int deleteArticle(@Param("articleId") int articleId);

    @Transactional
    @Modifying
    @Query("Update Article a set a.isDelete = 'N' where a.articleId = :articleId")
    int restoreArticle(@Param("articleId") int articleId);
}
