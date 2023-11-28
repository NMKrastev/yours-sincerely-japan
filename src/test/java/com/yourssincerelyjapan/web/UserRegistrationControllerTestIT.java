package com.yourssincerelyjapan.web;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.yourssincerelyjapan.model.dto.ReCaptchaResponseDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.service.ReCaptchaService;
import com.yourssincerelyjapan.service.UserService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
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
    private ReCaptchaService reCaptchaService;

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
    void testUserRegistrationIT() throws Exception {

        ReCaptchaResponseDTO responseDTO = new ReCaptchaResponseDTO();

        lenient().when(this.reCaptchaService.verify(any(String.class))).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users/registration")
                        .param("fullName", "Pesho")
                        .param("email", "pesho@example.com")
                        .param("password", "Qwerty1@")
                        .param("confirmPassword", "Qwerty1@")
                        .param("g-recaptcha-response", "validResponse")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());
    }

    private UserRegistrationDTO createUserRegistrationDTO() {

        List<UserRoleDTO> userRoles = List.of(new UserRoleDTO(1L, "USER"));

        return UserRegistrationDTO
                .builder()
                .fullName("Pesho")
                .email("pesho@example.com")
                .password("Qwerty1@")
                .confirmPassword("Qwerty1@")
                .createdOn(LocalDateTime.now())
                .roles(userRoles)
                .build();


    }

    @Test
    void testConfirmRegistration() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/account-verification")
                                .param("token", UUID.randomUUID().toString())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/login?isInvalidToken=true"));
    }
}