package com.nhnacademy.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeControl(@SessionAttribute(value = "login", required = false) String loginID
            , Model model) {

        model.addAttribute("loginId", loginID);

        return "index";
    }
}
