package com.nhnacademy.controller;

import com.nhnacademy.domain.Course;
import com.nhnacademy.domain.Subject;
import com.nhnacademy.domain.Teacher;
import com.nhnacademy.service.CourseService;
import com.nhnacademy.service.SubjectService;
import com.nhnacademy.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class CourseController {
    private TeacherService teacherService;
    private SubjectService subjectService;
    private CourseService courseService;

    public CourseController(TeacherService teacherService, SubjectService subjectService, CourseService courseService) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.courseService = courseService;
    }

    @Transactional
    @GetMapping("/course")
    String registerForm(Model model) {
        List<Teacher> teachers = teacherService.getTeachers();
        List<Subject> subjects = subjectService.getSubjects();

        model.addAttribute("teachers", teachers);
        model.addAttribute("subjects", subjects);

        return "courseForm";
    }

    @Transactional
    @PostMapping("/course")
    String register(@RequestParam long teacher,
                    @RequestParam long subject) {

        Teacher teacherById = teacherService.getTeacherById(teacher);
        Subject subjectById = subjectService.getSubjectById(subject);

        courseService.save(
                new Course(1L, teacherById, subjectById, new Date())
        );

        return "redirect:/";
    }

    @Transactional
    @GetMapping("/course/{courseId}")
    String modifyForm(@PathVariable long courseId, Model model) {
        List<Teacher> teachers = teacherService.getTeachers();
        List<Subject> subjects = subjectService.getSubjects();

        model.addAttribute("courseId",courseId);

        model.addAttribute("teachers", teachers);
        model.addAttribute("subjects", subjects);

        return "modifyForm";
    }

    @Transactional
    @PostMapping("/course/{courseId}")
    String modify(@PathVariable long courseId,
                  @RequestParam long teacher,
                  @RequestParam long subject){

        Teacher teacherById = teacherService.getTeacherById(teacher);
        Subject subjectById = subjectService.getSubjectById(subject);

        Course courseById = courseService.getCourseById(courseId);

        courseById.setSubject(subjectById);
        courseById.setTeacher(teacherById);

        courseService.modify(courseById);

        return "redirect:/";
    }

    @Transactional
    @GetMapping("/course/{courseId}/delete")
    String delete(@PathVariable long courseId){
        courseService.delete(courseId);
        return "redirect:/";
    }


}
