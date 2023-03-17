package com.nhnacademy.edu.repository.board;

import com.nhnacademy.edu.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom {
    public BoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public List<Board> getUserListGreaterThanVerifyLike(Pageable pageable, String keyword) {
        QBoard board = QBoard.board;
        QArticle article = QArticle.article;
        QUser createUser = QUser.user;
        QUser modifyUser = QUser.user;

        return from(board)
                .innerJoin(board.article, article).fetchJoin()
                .innerJoin(board.createUser, createUser).fetchJoin()
                .innerJoin(board.modifyUser, modifyUser).fetchJoin()
                .where(article.title.contains(keyword))
                .where(article.isDelete.contains("N"))
                .orderBy(board.boardId.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public List<Board> getAdminListGreaterThanVerifyLike(Pageable pageable, String keyword) {
        QBoard board = QBoard.board;
        QArticle article = QArticle.article;
        QUser createUser = QUser.user;
        QUser modifyUser = QUser.user;

        return from(board)
                .innerJoin(board.article, article).fetchJoin()
                .innerJoin(board.createUser, createUser).fetchJoin()
                .innerJoin(board.modifyUser, modifyUser).fetchJoin()
                .where(article.title.contains(keyword))
                .orderBy(board.boardId.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public List<Board> getHeartPost(Pageable pageable, String userId) {
        QBoard board = QBoard.board;
        QArticle article = QArticle.article;
        QUser createUser = QUser.user;
        QUser modifyUser = QUser.user;
        QHeart heart = QHeart.heart;

        return from(board)
                .innerJoin(board.article, article).fetchJoin()
                .innerJoin(board.createUser, createUser).fetchJoin()
                .innerJoin(board.modifyUser, modifyUser).fetchJoin()
                .innerJoin(heart.article, article).fetchJoin()
                .where(article.isDelete.contains("N"))
                .where(heart.user.userId.eq(userId))
                .orderBy(board.boardId.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
