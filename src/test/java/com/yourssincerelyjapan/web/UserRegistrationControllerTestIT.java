package com.yourssincerelyjapan.web;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.repository.ConfirmationRepository;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import com.yourssincerelyjapan.service.UserService;
import com.yourssincerelyjapan.service.impl.UserAccountConfirmationServiceImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserRegistrationControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private GreenMail greenMail;

    @Mock
    private ConfirmationRepository confirmationRepository;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        greenMail.start();
        greenMail.setUser(username, password);
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void testGetUserRegistration() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/registration")
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testUserRegistration() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users/registration")
                        .param("fullName", "Pesho")
                        .param("email", "pesho@example.com")
                        .param("password", "Qwerty1@")
                        .param("confirmPassword", "Qwerty1@")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void testConfirmRegistration() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/account-verification")
                                .param("token", UUID.randomUUID().toString())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/login?isInvalidToken=true"));
    }

    private User createUser() {

        return User
                .builder()
                .fullName("Pesho")
                .email("pesho@example.bg")
                .password("test")
                .createdOn(LocalDateTime.now())
                .enabled(false)
                .roles(List.of(new UserRole(UserRoleEnum.USER), new UserRole(UserRoleEnum.ADMIN)))
                .build();
    }
}