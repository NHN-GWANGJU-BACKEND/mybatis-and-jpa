package com.nhnacademy.edu.mapper;

import com.nhnacademy.edu.domain.Reply;
import com.nhnacademy.edu.domain.ReplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    List<Reply> findReplyByPostId(@Param("articleId") int articleId);

    int insertReply(ReplyVo replyVo);

    int updateReply(@Param("replyId") int replyId, @Param("content") String content);

    int deleteReply(@Param("replyId") int replyId);
}
