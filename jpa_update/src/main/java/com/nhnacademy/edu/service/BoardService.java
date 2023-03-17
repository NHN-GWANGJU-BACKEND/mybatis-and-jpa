package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.ArticleVO;
import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.entity.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    List<Board> findBoardListUser(Pageable pageable, String keyword);

    List<Board> findBoardListAdmin(Pageable pageable, String keyword);

    List<Board> findBoardHeartUser(Pageable pageable, String userId);

    Board findBoardByArticleId(int articleId);

    Board insertBoard(Article article, String userId);

    int updateBoard(int postId, ArticleVO articleVO, String userId);
}
