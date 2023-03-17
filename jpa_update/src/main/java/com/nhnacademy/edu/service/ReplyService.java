package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.ReplyVo;
import com.nhnacademy.edu.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> findReplyByPostId(int articleId);

    Reply insertReply(ReplyVo replyVo);

    int updateReply(int replyId, String content);

    int deleteReply(int replyId);
}
