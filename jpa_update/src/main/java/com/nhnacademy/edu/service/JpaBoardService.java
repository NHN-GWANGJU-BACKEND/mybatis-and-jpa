package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.ArticleVO;
import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.entity.Board;
import com.nhnacademy.edu.entity.User;
import com.nhnacademy.edu.repository.article.ArticleRepository;
import com.nhnacademy.edu.repository.board.BoardRepository;
import com.nhnacademy.edu.repository.user.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaBoardService implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public JpaBoardService(BoardRepository boardRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Board> findBoardListUser(Pageable pageable, String keyword) {
        return boardRepository.getUserListGreaterThanVerifyLike(pageable, keyword);
    }

    @Override
    public List<Board> findBoardListAdmin(Pageable pageable, String keyword) {
        return boardRepository.getAdminListGreaterThanVerifyLike(pageable, keyword);
    }

    @Override
    public List<Board> findBoardHeartUser(Pageable pageable, String userId) {
        return boardRepository.getHeartPost(pageable, userId);
    }

    @Override
    public Board findBoardByArticleId(int articleId) {
        Article article = articleRepository.findById(articleId).get();
        return boardRepository.findByArticle(article);
    }

    @Override
    @Transactional
    public Board insertBoard(Article article, String userId) {
        Article saveArticle = articleRepository.save(article);
        User user = userRepository.findById(userId).get();

        Board board = new Board(10, saveArticle, user, null);

        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public int updateBoard(int boardId, ArticleVO articleVO, String userId) {
        articleRepository.updateArticle(articleVO.getTitle(), articleVO.getContent(), articleVO.getDate(), articleVO.getId());
        User user = userRepository.findById(userId).get();
        return boardRepository.updateBoard(boardId, user);

    }
}
