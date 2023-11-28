package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserProfileController userProfileController;

    @Mock
    private UserService userService;

    @Test
    void getUserProfile() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/profile")
        ).andExpect(status().is3xxRedirection());

    }

    @Test
    void testUserProfile() {

    }

    @Test
    void updateFullName() {
    }

    @Test
    void updateEmail() {
    }

    @Test
    void updateProfilePicture() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void deleteProfilePicture() {
    }
}