//package com.nhnacademy.edu.repository;
//
//import com.nhnacademy.edu.config.RootConfig;
//import com.nhnacademy.edu.config.WebConfig;
//import com.nhnacademy.edu.entity.Article;
//import com.nhnacademy.edu.entity.Reply;
//import com.nhnacademy.edu.repository.article.ArticleRepository;
//import com.nhnacademy.edu.repository.reply.ReplyRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.ContextHierarchy;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@Transactional
//@ContextHierarchy({
//        @ContextConfiguration(classes = RootConfig.class),
//        @ContextConfiguration(classes = WebConfig.class)
//})
//class ReplyRepositoryTest {
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Test
//    void getReply() {
//        Article article = articleRepository.findById(0).get();
//
//        List<Reply> replies = replyRepository.findAllByArticleId(article);
//
//        assertThat(replies.size()).isEqualTo(3);
//    }
//
//    @Test
//    void updateReply(){
//        Reply reply = replyRepository.findById(0).get();
//        int updateReply = replyRepository.updateReply("update content", reply.getReplyId());
//        replyRepository.flush();
//
//        assertThat(updateReply).isEqualTo(1);
//    }
//
//
//}