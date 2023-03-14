package com.nhnacademy.edu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class HomeControllerTest {
    private MockHttpSession session;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        session.setAttribute("login","user");
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController())
                .build();
    }

    @Test
    void homeControl() throws Exception {
        mockMvc.perform(get("/")
                .session(session))
                .andExpect(model().attribute("loginId","user"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}