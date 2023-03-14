package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.domain.*;
import com.nhnacademy.edu.exception.FailedQueryExecuteException;
import com.nhnacademy.edu.exception.NotAllowAccessException;
import com.nhnacademy.edu.exception.NotFoundFileException;
import com.nhnacademy.edu.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ArticleControllerTest {
    private MockMvc mockMvc;
    private UserService userService;
    private ArticleService articleService;
    private ReplyService replyService;
    private ImageFileService imageFileService;
    private HeartService heartService;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        userService = mock(UserService.class);
        articleService = mock(ArticleService.class);
        replyService = mock(ReplyService.class);
        imageFileService = mock(ImageFileService.class);
        heartService = mock(HeartService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ArticleController(articleService, replyService, userService, imageFileService, heartService))
                .build();
    }

    @Test
    void articleListView_admin() throws Exception {
        User user = new User("admin", "12345", "admin", 9);
        List<Post> post = new ArrayList<>();
        when(userService.findById(anyString())).thenReturn(user);
        when(articleService.findPostListUser(anyInt(), anyInt(), anyString())).thenReturn(post);

        session.setAttribute("login", "admin");

        mockMvc.perform(get("/articles")
                        .session(session))
                .andExpect(view().name("articleList"))
                .andDo(print());
    }

    @Test
    void articleListView_user() throws Exception {
        List<Post> post = new ArrayList<>();
        User user = new User("", "", "", 0);
        when(articleService.findPostListUser(anyInt(), anyInt(), anyString())).thenReturn(post);

        mockMvc.perform(get("/articles?keyword=test")
                        .session(session))
                .andExpect(view().name("articleList"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("keyword", "test"))
                .andDo(print());
    }

    @Test
    void articleDetailView() throws Exception {
        User user = new User("admin", "12345", "admin", 9);
        Post post = new Post();
        List<Reply> replies = new ArrayList<>();
        List<ImageFile> imageFiles = new ArrayList<>();
        Heart heart = new Heart(1, "admin");

        session.setAttribute("login", "admin");

        when(replyService.findReplyByPostId(anyInt())).thenReturn(replies);
        when(articleService.findPostByArticleId(anyInt())).thenReturn(post);
        when(userService.findById(anyString())).thenReturn(user);
        when(imageFileService.findImageFiles(1)).thenReturn(imageFiles);
        when(heartService.findHeart(anyInt(), anyString())).thenReturn(heart);


        mockMvc.perform(get("/article/view?articleId=1")
                        .session(session))
                .andExpect(view().name("articleView"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("replies"))
                .andExpect(model().attributeExists("imageFiles"))
                .andExpect(model().attributeExists("heart"))
                .andExpect(model().attribute("user", user))
                .andExpect(status().isOk());
    }

    @Test
    void article_RegisterForm_not_login_user_not_allow_Access() throws Exception {

        Throwable th = catchThrowable(() -> mockMvc.perform(get("/article/register"))
                .andExpect(status().isOk())
                .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(NotAllowAccessException.class)
                .hasMessageContaining("해당페이지에 접근권한이 없습니다");
    }

    @Test
    void article_RegisterForm_login_user_Access() throws Exception {
        session.setAttribute("login", "admin");

        mockMvc.perform(get("/article/register")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("articleForm"))
                .andDo(print());
    }

    @Test
    void articleRegister_success() throws Exception {
        session.setAttribute("login", "admin");

        when(articleService.insertArticle(any())).thenReturn(1);
        when(articleService.insertPost(anyInt(), anyString())).thenReturn(1);
        when(imageFileService.insertImageFile(any(), anyInt())).thenReturn(1);

        mockMvc.perform(post("/article")
                        .param("title", "title")
                        .param("content", "content")
                        .session(session))
                .andDo(print())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    void articleRegister_failed() throws Exception {
        session.setAttribute("login", "admin");

        when(articleService.insertArticle(any())).thenReturn(-1);
        when(articleService.insertPost(anyInt(), anyString())).thenReturn(-1);
        when(imageFileService.insertImageFile(any(), anyInt())).thenReturn(-1);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/article")
                        .param("title", "title")
                        .param("content", "content")
                        .session(session))
                .andDo(print())
                .andExpect(status().isBadRequest()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void articleRegister_validation_failed() throws Exception {
        when(articleService.insertArticle(any())).thenReturn(1);
        when(articleService.insertPost(anyInt(), anyString())).thenReturn(1);

        mockMvc.perform(post("/article")
                        .param("title", "")
                        .param("content", "")
                        .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(redirectedUrl(null));
    }

    @Test
    void article_modify_form_not_login_allow_not_access() throws Exception {
        Throwable th = catchThrowable(() -> mockMvc.perform(get("/article/1/modify"))
                .andExpect(status().isBadRequest()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(NotAllowAccessException.class)
                .hasMessageContaining("해당페이지에 접근권한이 없습니다");
    }

    @Test
    void article_modify_form_login() throws Exception {
        session.setAttribute("login", "admin");
        Post post = new Post(1, null, null, null);

        when(articleService.findPostByArticleId(anyInt())).thenReturn(post);

        mockMvc.perform(get("/article/1/modify")
                        .session(session))
                .andExpect(model().attribute("post", post))
                .andExpect(status().isOk())
                .andExpect(view().name("articleModifyView"));
    }

    @Test
    void articleModify_success() throws Exception {
        session.setAttribute("login", "admin");

        when(articleService.updateArticle(any())).thenReturn(1);
        when(articleService.updatePost(anyInt(), anyString())).thenReturn(1);

        mockMvc.perform(post("/article/modify")
                        .session(session)
                        .param("postId", "1")
                        .param("articleId", "1")
                        .param("content", "content")
                        .param("title", "title"))
                .andExpect(redirectedUrl("/articles?pageNum=1"));
    }

    @Test
    void articleModify_failed() throws Exception {
        session.setAttribute("login", "admin");

        when(articleService.updateArticle(any())).thenReturn(-1);
        when(articleService.updatePost(anyInt(), anyString())).thenReturn(-1);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/article/modify")
                        .session(session)
                        .param("postId", "1")
                        .param("articleId", "1")
                        .param("content", "content")
                        .param("title", "title"))
                .andExpect(status().isBadRequest()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void articleModify_validation_failed() throws Exception {
        when(articleService.updateArticle(any())).thenReturn(1);
        when(articleService.updatePost(anyInt(), anyString())).thenReturn(1);

        mockMvc.perform(post("/article/modify")
                        .session(session)
                        .param("postId", "1")
                        .param("articleId", "1")
                        .param("content", "")
                        .param("title", ""))
                .andExpect(status().isBadRequest())
                .andExpect(redirectedUrl(null));
    }

    @Test
    void restore_article_success() throws Exception {
        session.setAttribute("login", "admin");
        when(articleService.restorePost(1)).thenReturn(1);

        mockMvc.perform(post("/article/restore")
                        .session(session)
                        .param("articleId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    void restore_article_login_failed() {
        session.setAttribute("login", "user");
        when(articleService.restorePost(anyInt())).thenReturn(1);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/article/restore")
                .session(session)
                .param("articleId", "1")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(NotAllowAccessException.class)
                .hasMessageContaining("해당페이지에 접근권한이 없습니다");
    }

    @Test
    void restore_article_data_update_failed() {
        session.setAttribute("login", "admin");
        when(articleService.restorePost(1)).thenReturn(0);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/article/restore")
                .session(session)
                .param("articleId", "1")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void articleDetailDelete() throws Exception {
        when(articleService.deletePost(1)).thenReturn(1);

        mockMvc.perform(post("/article/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    void delete_article_data_update_failed() {
        session.setAttribute("login", "admin");
        when(articleService.restorePost(1)).thenReturn(0);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/article/1/delete")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void download_file_success() throws Exception {
        mockMvc.perform(get("/article/download")
                        .param("saveName", "test"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void Heart_click_success() throws Exception {
        when(heartService.insertHeart(1,"taewon")).thenReturn(1);

        mockMvc.perform(post("/article/heart")
                .param("articleId","1")
                .param("userId","taewon")
                .param("heart",""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/view?articleId=1"));

    }

    @Test
    void Heart_click_failed() throws Exception {
        when(heartService.insertHeart(1,"taewon")).thenReturn(0);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/article/heart")
                        .param("articleId","1")
                        .param("userId","taewon")
                        .param("heart","")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void Heart_unClick_success() throws Exception {
        when(heartService.deleteHeart(1,"taewon")).thenReturn(1);

        mockMvc.perform(post("/article/heart")
                        .param("articleId","1")
                        .param("userId","taewon")
                        .param("heart","heart"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/view?articleId=1"));

    }

    @Test
    void Heart_unClick_failed() throws Exception {
        when(heartService.deleteHeart(1,"taewon")).thenReturn(0);

        Throwable th = catchThrowable(() -> mockMvc.perform(post("/article/heart")
                .param("articleId","1")
                .param("userId","taewon")
                .param("heart","heart")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(FailedQueryExecuteException.class)
                .hasMessageContaining("데이터가 DB에 정상적으로 저장되지 않았습니다");
    }

    @Test
    void search_keyword() throws Exception {
        mockMvc.perform(post("/article/search")
                .param("keyword","taewon"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles?keyword=taewon"));
    }


}