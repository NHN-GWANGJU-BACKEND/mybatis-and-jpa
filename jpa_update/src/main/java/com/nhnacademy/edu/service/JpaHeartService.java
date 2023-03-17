package com.nhnacademy.edu.service;

import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.entity.Heart;
import com.nhnacademy.edu.entity.User;
import com.nhnacademy.edu.repository.article.ArticleRepository;
import com.nhnacademy.edu.repository.heart.HeartRepository;
import com.nhnacademy.edu.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaHeartService implements HeartService {

    private final HeartRepository heartRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public JpaHeartService(HeartRepository heartRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.heartRepository = heartRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Heart findHeart(int articleId, String userId) {
        Heart.Pk pk = new Heart.Pk(articleId, userId);
        Optional<Heart> heart = heartRepository.findById(pk);
        return heart.orElse(null);
    }

    @Override
    public Heart insertHeart(int articleId, String userId) {
        Article article = articleRepository.findById(articleId).get();
        User user = userRepository.findById(userId).get();

        Heart heart = new Heart();

        heart.setPk(new Heart.Pk(articleId, userId));
        heart.setArticle(article);
        heart.setUser(user);

        return heartRepository.saveAndFlush(heart);
    }

    @Override
    public int deleteHeart(int articleId, String userId) {
        return heartRepository.deleteHeart(new Heart.Pk(articleId, userId));
    }
}
