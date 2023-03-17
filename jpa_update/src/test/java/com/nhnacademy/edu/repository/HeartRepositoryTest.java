//package com.nhnacademy.edu.repository;
//
//import com.nhnacademy.edu.config.RootConfig;
//import com.nhnacademy.edu.config.WebConfig;
//import com.nhnacademy.edu.entity.Article;
//import com.nhnacademy.edu.entity.Heart;
//import com.nhnacademy.edu.entity.User;
//import com.nhnacademy.edu.repository.article.ArticleRepository;
//import com.nhnacademy.edu.repository.heart.HeartRepository;
//import com.nhnacademy.edu.repository.user.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.ContextHierarchy;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@Transactional
//@ContextHierarchy({
//        @ContextConfiguration(classes = RootConfig.class),
//        @ContextConfiguration(classes = WebConfig.class)
//})
//class HeartRepositoryTest {
//
//    @Autowired
//    HeartRepository heartRepository;
//
//    @Autowired
//    ArticleRepository articleRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    @Test
//    void findByHeart() {
//        Heart heart = heartRepository.findById(new Heart.Pk(0, "admin")).get();
//
//        assertThat(heart).isNotNull();
//    }
//
//
//    @Test
//    void insertHeart() {
//        User user = userRepository.findById("taewon").get();
//        Article article = articleRepository.findById(4).get();
//
//        Heart heart = heartRepository.save(new Heart(new Heart.Pk(article.getArticleId(), user.getUserId()), article, user));
//
//        assertThat(heart).isNotNull();
//        assertThat(heart.getArticle().getTitle()).isEqualTo("test5");
//    }
//
//
//    @Test
//    void deleteHeart() {
//        Heart heart = heartRepository.findById(new Heart.Pk(2, "admin")).get();
//        heartRepository.delete(heart);
//
//        Optional<Heart> heart1 = heartRepository.findById(new Heart.Pk(2, "admin"));
//
//        assertThat(heart1.isPresent()).isFalse();
//
//    }
//
//}