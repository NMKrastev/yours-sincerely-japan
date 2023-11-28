package com.yourssincerelyjapan.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUserLogin() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/login")
        ).andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login"));

    }
}