package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.domain.ReplyVo;
import com.nhnacademy.edu.exception.FailedQueryExecuteException;
import com.nhnacademy.edu.service.ArticleService;
import com.nhnacademy.edu.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final ArticleService articleService;

    public ReplyController(ReplyService replyService, ArticleService articleService) {
        this.replyService = replyService;
        this.articleService = articleService;
    }

    @PostMapping("/reply")
    public String replyRegister(@RequestParam String content,
                                @RequestParam int articleId,
                                @SessionAttribute("login") String loginId) {
        replyService.insertReply(new ReplyVo(loginId, articleId, content));
        articleService.updateReplyCountPlus(articleId);

        return "redirect:/article/view?articleId=" + articleId;
    }

    @PostMapping("/reply/modify")
    public String replyModify(@RequestParam int replyId,
                              @RequestParam int articleId,
                              @RequestParam String content) {

        int updateReply = replyService.updateReply(replyId, content);

        if (updateReply != 1) {
            throw new FailedQueryExecuteException();
        }

        return "redirect:/article/view?articleId=" + articleId;
    }

    @PostMapping("/reply/delete")
    public String replyDelete(@RequestParam int replyId,
                              @RequestParam int articleId) {

        int replyDelete = replyService.deleteReply(replyId);
        int updateReplyCount = articleService.updateReplyCountMinus(articleId);

        if (replyDelete != 1 || updateReplyCount != 1) {
            throw new FailedQueryExecuteException();
        }

        return "redirect:/article/view?articleId=" + articleId;
    }


}
