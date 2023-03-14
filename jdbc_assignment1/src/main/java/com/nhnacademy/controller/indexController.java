package com.nhnacademy.controller;

import com.nhnacademy.domain.Course;
import com.nhnacademy.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.nhnacademy.method.SessionLogin.getSessionLogin;

@Controller
public class indexController {
    private final CourseService courseService;

    public indexController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String homeControl(HttpServletRequest request, Model model) {
        List<Course> courses = courseService.getCourses();

        String loginID = getSessionLogin(request);

        model.addAttribute("courses", courses);
        model.addAttribute("loginId", loginID);

        return "index";
    }
}
