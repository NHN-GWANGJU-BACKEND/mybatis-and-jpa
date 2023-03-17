package com.nhnacademy.edu.service;

public interface ArticleService {

    int updateReplyCountPlus(int articleId);

    int updateReplyCountMinus(int articleId);

    int deleteArticle(int articleId);

    int restoreArticle(int articleId);

}
