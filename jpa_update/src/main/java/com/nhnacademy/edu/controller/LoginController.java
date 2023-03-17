package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;


@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @GetMapping("/login")
    public String getLogin(@SessionAttribute(value = "login", required = false) String loginID) {
        if (Objects.isNull(loginID)) {
            return "loginForm";
        }

        return "redirect:/";
    }

    @Transactional
    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        HttpServletRequest req) {

        if (userService.match(id, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("login", id);
            return "redirect:/";
        }
        return "redirect:/login";
    }
}
