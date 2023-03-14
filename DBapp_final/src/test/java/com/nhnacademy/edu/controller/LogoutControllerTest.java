package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class LogoutControllerTest {
    private MockMvc mockMvc;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        this.session = new MockHttpSession();
        mockMvc = MockMvcBuilders.standaloneSetup(new LogoutController())
                .build();
    }
    @Test
    @DisplayName("로그아웃 정상작동")
    void logout() throws Exception {
        session.setAttribute("login","customer");

        mockMvc.perform(get("/logout")
                        .session(session))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("로그인이 안된 상태로 로그아웃 시도")
    void try_logout_but_notExist_loginSession() throws Exception {
        mockMvc.perform(get("/logout")
                        .session(session))
                .andExpect(view().name("loginForm"));
    }
}
