package com.nhnacademy.edu.repository;

import com.nhnacademy.edu.config.RootConfig;
import com.nhnacademy.edu.config.WebConfig;
import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.repository.article.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;


    @Test
    void updateArticle() {
        articleRepository.updateArticle("update title", "update content", new Timestamp(new Date().getTime()), 0);

        Article article = articleRepository.findById(0).get();

        assertThat(article.getTitle()).isEqualTo("update title");
    }

    @Test
    void insertArticle() {
        Article article = new Article(5, "test5", "test5", null, "N", null, 0);

        article.setCreatedAt(new Timestamp(new Date().getTime()));
        article.setUpdateAt(new Timestamp(new Date().getTime()));

        articleRepository.saveAndFlush(article);

        Article article1 = articleRepository.findById(5).get();

        assertThat(article1.getTitle()).isEqualTo(article.getTitle());
    }

    @Test
    void updateReplyCountPlus() {
        articleRepository.updateReplyCountPlus(0);

        Article article = articleRepository.findById(0).get();

        assertThat(article.getReplyCount()).isEqualTo(4);
    }

    @Test
    void updateReplyCountMinus() {
        articleRepository.updateReplyCountMinus(0);

        Article article = articleRepository.findById(0).get();

        assertThat(article.getReplyCount()).isEqualTo(2);
    }


    @Test
    void delete_article() {
        int delete = articleRepository.deleteArticle(0);

        articleRepository.flush();

        Article article = articleRepository.findById(0).get();

        assertThat(delete).isEqualTo(1);
        assertThat(article.getIsDelete()).isEqualTo("Y");
    }

    @Test
    void restore_article() {
        int restore = articleRepository.restoreArticle(0);

        articleRepository.flush();

        Article article = articleRepository.findById(0).get();

        assertThat(restore).isEqualTo(1);
    }

    @Test
    void getArticles(){
        List<Article> all = articleRepository.findAll();

        assertThat(all.size()).isEqualTo(5);
    }

}