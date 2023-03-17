package com.nhnacademy.edu.service;

import com.nhnacademy.edu.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class JpaArticleService implements ArticleService {

    private ArticleRepository articleRepository;

    public JpaArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public int updateReplyCountPlus(int articleId) {
        return articleRepository.updateReplyCountPlus(articleId);
    }

    @Override
    public int updateReplyCountMinus(int articleId) {
        return articleRepository.updateReplyCountMinus(articleId);
    }

    @Override
    public int deleteArticle(int articleId) {
        articleRepository.deleteArticle(articleId);
        articleRepository.flush();
        return 1;
    }

    @Override
    public int restoreArticle(int articleId) {
        articleRepository.restoreArticle(articleId);
        articleRepository.flush();
        return 1;
    }
}
