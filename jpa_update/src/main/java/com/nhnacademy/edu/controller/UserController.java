package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.entity.Board;
import com.nhnacademy.edu.service.BoardService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class UserController {
    private final BoardService boardService;

    public UserController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/user")
    public String userLikeListView(@RequestParam String userId,
                                   Pageable pageable,
                                   Model model) {

        List<Board> userHeartPost = boardService.findBoardHeartUser(pageable, userId);

        model.addAttribute("posts", userHeartPost);
        model.addAttribute("userId", userId);

        return "userLike";
    }
}
