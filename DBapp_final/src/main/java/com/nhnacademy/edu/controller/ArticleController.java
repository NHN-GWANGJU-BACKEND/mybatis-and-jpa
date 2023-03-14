package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.domain.*;
import com.nhnacademy.edu.exception.FailedQueryExecuteException;
import com.nhnacademy.edu.exception.NotAllowAccessException;
import com.nhnacademy.edu.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
public class ArticleController {
    private final int OFFSET = 20;
    private final ArticleService articleService;
    private final ReplyService replyService;
    private final UserService userService;
    private final ImageFileService imageFileService;
    private final HeartService heartService;

    public ArticleController(ArticleService articleService, ReplyService replyService,
                             UserService userService, ImageFileService imageFileService, HeartService heartService) {
        this.articleService = articleService;
        this.replyService = replyService;
        this.userService = userService;
        this.imageFileService = imageFileService;
        this.heartService = heartService;
    }

    @Transactional
    @GetMapping("/articles")
    public String articleListView(@RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(defaultValue = "1") int pageNum,
                                  @SessionAttribute(value = "login", required = false) String loginId,
                                  Model model) {
        User user = new User("", "", "", 0);

        if (Objects.nonNull(loginId)) {
            user = userService.findById(loginId);
        }

        List<Post> posts = getPosts(pageNum, user, keyword);
        Pagination pagination = getPagination(pageNum, user, keyword);

        model.addAttribute("posts", posts);
        model.addAttribute("pagination", pagination);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);

        return "articleList";
    }

    private Pagination getPagination(int pageNum, User user, String keyword) {
        Pagination pagination;
        if (user == null || user.getVerify() != 9) {
            pagination = new Pagination(articleService.getUserTotalCount(keyword), pageNum, this.OFFSET);
        } else {
            pagination = new Pagination(articleService.getAdminTotalCount(keyword), pageNum, this.OFFSET);
        }
        return pagination;
    }

    private List<Post> getPosts(int pageNum, User user, String keyword) {
        List<Post> posts;
        if (user == null || user.getVerify() != 9) {
            posts = articleService.findPostListUser(pageNum, OFFSET, keyword);
        } else {
            posts = articleService.findPostListAdmin(pageNum, OFFSET, keyword);
        }
        return posts;
    }


    @Transactional
    @GetMapping("/article/view")
    public String articleDetailView(@RequestParam int articleId,
                                    @SessionAttribute(value = "login", required = false) String loginId,
                                    Model model) {

        User user = new User("", "", "", 0);

        if (Objects.nonNull(loginId)) {
            user = userService.findById(loginId);
        }

        Post post = articleService.findPostByArticleId(articleId);
        List<Reply> replies = replyService.findReplyByPostId(articleId);
        List<ImageFile> imageFiles = imageFileService.findImageFiles(articleId);
        Heart heart = heartService.findHeart(articleId, loginId);

        model.addAttribute("post", post);
        model.addAttribute("replies", replies);
        model.addAttribute("user", user);
        model.addAttribute("imageFiles", imageFiles);
        model.addAttribute("heart", heart);

        return "articleView";
    }

    @Transactional
    @GetMapping("/article/register")
    public String articleRegisterForm(@SessionAttribute(value = "login", required = false) String loginUser) {
        if (Objects.isNull(loginUser)) {
            throw new NotAllowAccessException("비로그인 유저");
        }

        return "articleForm";
    }

    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/article")
    public String articleRegister(@Valid @ModelAttribute ArticleModel article,
                                  @SessionAttribute("login") String loginId) {
        ArticleVO articleVO = new ArticleVO(0, article.getTitle(), article.getContent(), null);

        int articleInsert = articleService.insertArticle(articleVO);
        int postInsert = articleService.insertPost(articleVO.getId(), loginId);
        int imageInsert = imageFileService.insertImageFile(article.getMultipartFiles(), articleVO.getId());

        if (articleInsert != 1 || postInsert != 1 || imageInsert == -1) {
            throw new FailedQueryExecuteException();
        }

        return "redirect:/articles";
    }

    @Transactional
    @GetMapping("/article/{articleId}/modify")
    public String articleModifyFormView(@PathVariable int articleId,
                                        @SessionAttribute(value = "login", required = false) String loginUser,
                                        Model model) {
        if (Objects.isNull(loginUser)) {
            throw new NotAllowAccessException("비로그인 유저");
        }

        Post post = articleService.findPostByArticleId(articleId);
        model.addAttribute("post", post);

        return "articleModifyView";
    }

    @Transactional
    @PostMapping("/article/modify")
    public String articleModify(@Valid @ModelAttribute ArticleModel article,
                                @RequestParam int postId,
                                @RequestParam int articleId,
                                @SessionAttribute(value = "login", required = false) String loginId) {
        ArticleVO articleVO =
                new ArticleVO(articleId, article.getTitle(), article.getContent(), new Timestamp(new Date().getTime()));

        int articleUpdate = articleService.updateArticle(articleVO);
        int postUpdate = articleService.updatePost(postId, loginId); // 수정자 변환하기 위한 쿼리문

        if (articleUpdate != 1 || postUpdate != 1) {
            throw new FailedQueryExecuteException();
        }

        return "redirect:/articles?pageNum=1";
    }

    @Transactional
    @PostMapping("/article/restore")
    public String restoreArticle(@RequestParam int articleId,
                                 @SessionAttribute(value = "login", required = false) String loginId) {
        if (!loginId.equals("admin")) {
            throw new NotAllowAccessException("비관리자 계정");
        }

        int restore = articleService.restorePost(articleId);

        if (restore != 1) {
            throw new FailedQueryExecuteException();
        }

        return "redirect:/articles";
    }

    @Transactional
    @PostMapping("/article/{articleId}/delete")
    public String articleDetailDelete(@PathVariable int articleId) {
        int postDelete = articleService.deletePost(articleId);

        if (postDelete != 1) {
            throw new FailedQueryExecuteException();
        }

        return "redirect:/articles";
    }

    @GetMapping("/article/download")
    public void downloadFile(@RequestParam String saveName, HttpServletResponse resp) {
        imageFileService.downloadImageFile(saveName, resp);
    }

    @Transactional
    @PostMapping("/article/heart")
    public String likeClick(@RequestParam int articleId,
                            @RequestParam String userId,
                            @RequestParam String heart) {

        updateHeart(articleId, userId, heart);

        return "redirect:/article/view?articleId=" + articleId;
    }

    private void updateHeart(int articleId, String userId, String heart) {
        int updateHeart;

        if (heart.equals("")) {
            updateHeart = heartService.insertHeart(articleId, userId);
        } else {
            updateHeart = heartService.deleteHeart(articleId, userId);
        }

        if (updateHeart != 1) {
            throw new FailedQueryExecuteException();
        }
    }

    @PostMapping("/article/search")
    public String searchKeyword(@RequestParam String keyword) throws UnsupportedEncodingException {
        String encodeKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        return "redirect:/articles?keyword=" + encodeKeyword;
    }
}
