package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.ReplyVo;
import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.entity.Reply;
import com.nhnacademy.edu.entity.User;
import com.nhnacademy.edu.repository.article.ArticleRepository;
import com.nhnacademy.edu.repository.reply.ReplyRepository;
import com.nhnacademy.edu.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JpaReplyService implements ReplyService {
    private final ReplyRepository replyRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public JpaReplyService(ReplyRepository replyRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.replyRepository = replyRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Reply> findReplyByPostId(int articleId) {
        Article article = articleRepository.findById(articleId).get();
        return replyRepository.findAllByArticleId(article);
    }

    @Override
    public Reply insertReply(ReplyVo replyVo) {
        Article article = articleRepository.findById(replyVo.getArticleId()).get();
        User user = userRepository.findById(replyVo.getUserId()).get();
        Reply reply = new Reply(10, replyVo.getContent(), new Date(), article, user);
        return replyRepository.save(reply);
    }

    @Override
    public int updateReply(int replyId, String content) {
        return replyRepository.updateReply(content, replyId);
    }


    @Override
    public int deleteReply(int replyId) {
        return replyRepository.deleteByReplyId(replyId);
    }
}
