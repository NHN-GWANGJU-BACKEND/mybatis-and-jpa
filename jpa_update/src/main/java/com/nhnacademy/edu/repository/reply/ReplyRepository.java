package com.nhnacademy.edu.repository.reply;

import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.entity.Heart;
import com.nhnacademy.edu.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    List<Reply> findAllByArticleId(Article article);

    @Modifying
    @Transactional
    @Query("update Reply r set r.content=:content where r.replyId = :replyId")
    int updateReply(@Param("content") String content, @Param("replyId") int replyId);

    @Transactional
    int deleteByReplyId(int replyId);
}
