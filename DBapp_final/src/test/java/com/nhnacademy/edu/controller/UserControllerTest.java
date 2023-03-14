package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.domain.Heart;
import com.nhnacademy.edu.domain.Post;
import com.nhnacademy.edu.service.HeartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private HeartService heartService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        heartService = mock(HeartService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(heartService))
                .build();
    }

    @Test
    void userLikeListView() throws Exception {
        List<Post> userHeartPost = new ArrayList<>();
        when(heartService.findUserHeartPost(anyInt(),anyInt(),anyString())).thenReturn(userHeartPost);
        when(heartService.getTotalCount(anyString())).thenReturn(1);

        mockMvc.perform(get("/user")
                .param("userId","taewon")
                .param("pageNum","1"))
                .andExpect(model().attribute("posts",userHeartPost))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(model().attribute("userId","taewon"))
                .andExpect(status().isOk())
                .andExpect(view().name("userLike"));
    }
}