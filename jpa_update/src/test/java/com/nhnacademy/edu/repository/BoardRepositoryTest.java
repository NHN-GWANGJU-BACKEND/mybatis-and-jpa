//package com.nhnacademy.edu.repository;
//
//import com.nhnacademy.edu.config.RootConfig;
//import com.nhnacademy.edu.config.WebConfig;
//import com.nhnacademy.edu.entity.Board;
//import com.nhnacademy.edu.entity.User;
//import com.nhnacademy.edu.repository.board.BoardRepository;
//import com.nhnacademy.edu.repository.user.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
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
//class BoardRepositoryTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Test
//    void findBoardByArticleId() {
//        Board board = boardRepository.findById(0).get();
//
//        assertThat(board.getArticleId().getTitle()).isEqualTo("test");
//        assertThat(board.getCreateUserId().getUserName()).isEqualTo("admin");
//        assertThat(board.getModifyUser().getUserName()).isEqualTo("없음");
//    }
//
//    @Test
//    void findBoardByVerify_Admin() {
//        List<Board> boards = boardRepository.findBoardByCreateUserId_VerifyGreaterThan(8, Pageable.ofSize(20));
//
//        assertThat(boards.size()).isEqualTo(1);
//    }
//
//    @Test
//    void findBoardByVerify_User() {
//        List<Board> boards = boardRepository.findBoardByCreateUserId_VerifyGreaterThan(0);
//
//        assertThat(boards.size()).isEqualTo(5);
//    }
//
//    @Test
//    void update_post() {
//        User user = userRepository.findById("taewon").get();
//
//        boardRepository.updatePost(0, user);
//
//        Board board = boardRepository.findById(0).get();
//
//        assertThat(board.getModifyUser().getUserName()).isEqualTo("임태원");
//    }
//
//
//    @Test
//    void getTotalCount(){
//        int es = boardRepository.countByArticleId_TitleLike("%es%");
//
//        assertThat(es).isEqualTo(5);
//    }
//
//
//
//}