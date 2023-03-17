package com.nhnacademy.edu.repository.board;

import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.entity.Board;
import com.nhnacademy.edu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardRepositoryCustom {

    Board findByArticle(Article article);

    @Transactional
    @Modifying
    @Query("UPDATE Board b set b.modifyUser = :user where b.boardId = :boardId")
    int updateBoard(@Param("boardId") int boardId, @Param("user") User user);
}