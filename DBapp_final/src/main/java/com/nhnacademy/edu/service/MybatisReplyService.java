package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.Reply;
import com.nhnacademy.edu.domain.ReplyVo;
import com.nhnacademy.edu.mapper.ReplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MybatisReplyService implements ReplyService {
    private final ReplyMapper replyMapper;

    public MybatisReplyService(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    @Override
    public List<Reply> findReplyByPostId(int articleId) {
        return replyMapper.findReplyByPostId(articleId);
    }

    @Override
    public int insertReply(ReplyVo replyVo) {
        return replyMapper.insertReply(replyVo);
    }

    @Override
    public int updateReply(int replyId, String content) {
        return replyMapper.updateReply(replyId, content);
    }


    @Override
    public int deleteReply(int replyId) {
        return replyMapper.deleteReply(replyId);
    }
}
