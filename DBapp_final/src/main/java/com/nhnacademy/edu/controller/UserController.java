package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.domain.Pagination;
import com.nhnacademy.edu.domain.Post;
import com.nhnacademy.edu.service.HeartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final HeartService heartService;

    private final int OFFSET = 20;

    public UserController(HeartService heartService) {
        this.heartService = heartService;
    }

    @GetMapping("/user")
    public String userLikeListView(@RequestParam String userId,
                                   @RequestParam(defaultValue = "1") int pageNum,
                                   Model model) {

        List<Post> userHeartPost = heartService.findUserHeartPost(pageNum, OFFSET, userId);
        Pagination pagination = new Pagination(heartService.getTotalCount(userId), pageNum, this.OFFSET);

        model.addAttribute("posts", userHeartPost);
        model.addAttribute("pagination", pagination);
        model.addAttribute("userId", userId);

        return "userLike";
    }
}
