package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.Reply;
import com.nhnacademy.edu.domain.ReplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyService {
    List<Reply> findReplyByPostId(int articleId);

    int insertReply(ReplyVo replyVo);

    int updateReply(@Param("replyId") int replyId, @Param("content") String content);

    int deleteReply(int replyId);
}
