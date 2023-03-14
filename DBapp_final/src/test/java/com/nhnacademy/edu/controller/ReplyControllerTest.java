package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.exception.FailedQueryExecuteException;
import com.nhnacademy.edu.service.ArticleService;
import com.nhnacademy.edu.service.ReplyService;
import com.nhnacademy.edu.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ReplyControllerTest {
    private MockMvc mockMvc;
    private UserService userService;
    private ArticleService articleService;
    private ReplyService replyService;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        session.setAttribute("login", "admin");
        userService = mock(UserService.class);
        articleService = mock(ArticleService.class);
        replyService = mock(ReplyService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ReplyController(replyService, articleService))
                .build();
    }

    @Test
    void replyRegister_success() throws Exception {
        when(replyService.insertReply(any())).thenReturn(1);
        when(articleService.updateReplyCountPlus(1)).thenReturn(1);

        mockMvc.perform(post("/reply")
                        .session(session)
                        .param("articleId", "1")
                        .param("content", "content"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/view?articleId=1"));
    }

    @Test
    void replyRegister_failed() throws Exception {
        when(replyService.insertReply(any())).thenReturn(1);
        when(articleService.updateReplyCountPlus(1)).thenReturn(0);


        Throwable th = catchThrowable(() -> mockMvc.perform(post("/reply")
                .session(session)
                .param("articleId", "1")
                .param("content", "content")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void replyModify_success() throws Exception {
        when(replyService.updateReply(1, "content")).thenReturn(1);

        mockMvc.perform(post("/reply/modify")
                        .param("articleId", "1")
                        .param("replyId", "1")
                        .param("content", "content"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/view?articleId=1"));
    }

    @Test
    void replyModify_failed() throws Exception {
        when(replyService.updateReply(1, "content")).thenReturn(0);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/reply/modify")
                        .param("articleId", "1")
                        .param("replyId", "1")
                        .param("content", "content")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void replyDelete_success() throws Exception {
        when(replyService.deleteReply(1)).thenReturn(1);
        when(articleService.updateReplyCountMinus(1)).thenReturn(1);

        mockMvc.perform(post("/reply/delete")
                        .param("articleId", "1")
                        .param("replyId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/view?articleId=1"));
    }

    @Test
    void replyDelete_failed() throws Exception {
        when(replyService.deleteReply(1)).thenReturn(0);
        when(articleService.updateReplyCountMinus(1)).thenReturn(0);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/reply/delete")
                .param("articleId", "1")
                .param("replyId", "1")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }
}