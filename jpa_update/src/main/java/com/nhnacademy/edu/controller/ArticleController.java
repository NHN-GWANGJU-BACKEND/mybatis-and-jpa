package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.domain.ArticleModel;
import com.nhnacademy.edu.domain.ArticleVO;
import com.nhnacademy.edu.entity.*;
import com.nhnacademy.edu.exception.FailedQueryExecuteException;
import com.nhnacademy.edu.exception.NotAllowAccessException;
import com.nhnacademy.edu.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    private final ArticleService articleService;
    private final ReplyService replyService;
    private final UserService userService;
    private final ImageFileService imageFileService;
    private final HeartService heartService;
    private final BoardService boardService;


    public ArticleController(ArticleService articleService, ReplyService replyService, UserService userService,
                             ImageFileService imageFileService, HeartService heartService, BoardService boardService) {
        this.articleService = articleService;
        this.replyService = replyService;
        this.userService = userService;
        this.imageFileService = imageFileService;
        this.heartService = heartService;
        this.boardService = boardService;
    }

    @Transactional
    @GetMapping("/articles")
    public String articleListView(@RequestParam(defaultValue = "") String keyword,
                                  Pageable pageable,
                                  @SessionAttribute(value = "login", required = false) String loginId,
                                  Model model) {
        User user = new User("", "", "", 0);

        if (Objects.nonNull(loginId)) {
            user = userService.findById(loginId);
        }

        List<Board> boards = getPosts(pageable, user, keyword);

        model.addAttribute("boards", boards);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);

        return "articleList";
    }


    private List<Board> getPosts(Pageable pageable, User user, String keyword) {
        List<Board> posts;
        if (user == null || user.getVerify() != 9) {
            posts = boardService.findBoardListUser(pageable, keyword);
        } else {
            posts = boardService.findBoardListAdmin(pageable, keyword);
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

        Board board = boardService.findBoardByArticleId(articleId);
        List<Reply> replies = replyService.findReplyByPostId(articleId);
        List<ImageFile> imageFiles = imageFileService.findImageFiles(articleId);
        Heart heart = heartService.findHeart(articleId, loginId);

        model.addAttribute("board", board);
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

        Article article1 = new Article();
        article1.setArticleId(10);
        article1.setCreatedAt(new Date());
        article1.setContent(article.getContent());
        article1.setTitle(article.getTitle());
        article1.setIsDelete("N");
        article1.setReplyCount(0);
        article1.setUpdateAt(new Date());

        boardService.insertBoard(article1, loginId);
        imageFileService.insertImageFile(article.getMultipartFiles(), articleVO.getId());

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

        Board board = boardService.findBoardByArticleId(articleId);
        model.addAttribute("board", board);

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

        boardService.updateBoard(postId, articleVO, loginId);

        return "redirect:/articles?pageNum=1";
    }

    @Transactional
    @PostMapping("/article/restore")
    public String restoreArticle(@RequestParam int articleId,
                                 @SessionAttribute(value = "login", required = false) String loginId) {
        if (!loginId.equals("admin")) {
            throw new NotAllowAccessException("비관리자 계정");
        }

        int restore = articleService.restoreArticle(articleId);

        if (restore != 1) {
            throw new FailedQueryExecuteException();
        }

        return "redirect:/articles";
    }

    @Transactional
    @PostMapping("/article/{articleId}/delete")
    public String articleDetailDelete(@PathVariable int articleId) {
        int postDelete = articleService.deleteArticle(articleId);

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
            heartService.insertHeart(articleId, userId);
        } else {
            heartService.deleteHeart(articleId, userId);
        }
    }

    @PostMapping("/article/search")
    public String searchKeyword(@RequestParam String keyword) throws UnsupportedEncodingException {
        String encodeKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        return "redirect:/articles?keyword=" + encodeKeyword;
    }
}
