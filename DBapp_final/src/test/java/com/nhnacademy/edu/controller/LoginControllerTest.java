package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.controller.LoginController;
import com.nhnacademy.edu.domain.User;
import com.nhnacademy.edu.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class LoginControllerTest {
    private UserService userService;
    private MockMvc mockMvc;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        this.session = new MockHttpSession();

        this.userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(userService))
                .build();
    }

    @Test
    void LoginCustomerSessionExist() throws Exception {
        User user = new User("customer", "12345", "손님",9);

        when(userService.findById(anyString())).thenReturn(user);

        session.setAttribute("login", "customer");

        mockMvc.perform(get("/login")
                        .session(session))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void LoginSessionNotExist() throws Exception {
        mockMvc.perform(get("/login")
                        .session(session))
                .andDo(print())
                .andExpect(view().name("loginForm"));
    }

    @Test
    void postLogin() throws Exception {
        User user = new User("customer", "12345", "손님",9);

        when(userService.match(anyString(), anyString()))
                .thenReturn(true);

        when(userService.findById(anyString())).thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("id","customer1")
                        .param("password","12345")
                        .session(session))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void notMatchLoginID() throws Exception {
        when(userService.match(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("id","customer1")
                        .param("password","12345")
                        .session(session))
                .andExpect(redirectedUrl("/login"));
    }


}