package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.repository.ConfirmationRepository;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import com.yourssincerelyjapan.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserAccountConfirmationServiceImplTest {

    @Mock
    private UserAccountConfirmationService confirmationService;

    @Mock
    private UserService userService;

    @Mock
    private ConfirmationRepository confirmationRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        confirmationService =
                new UserAccountConfirmationServiceImpl(confirmationRepository, userService);
    }

    @Test
    void saveVerificationToken() {

        User user = createTestUser();

        UserAccountConfirmation accountConfirmation =
                new UserAccountConfirmation(user);

        confirmationService.saveVerificationToken(accountConfirmation);

        verify(confirmationRepository, times(1)).save(accountConfirmation);

    }

    @Test
    void getVerificationToken() {

        String token = "token";

        User user = createTestUser();

        UserAccountConfirmation accountConfirmation =
                new UserAccountConfirmation(user);

        when(confirmationRepository.findByToken(token)).thenReturn(accountConfirmation);

        UserAccountConfirmation result = confirmationService.getVerificationToken(token);

        assertEquals(result.getUser().getEmail(), accountConfirmation.getUser().getEmail());
        verify(confirmationRepository, times(1)).findByToken(token);
        assertNotNull(result);
    }

    @Test
    void accountVerification() {

        String token = "token";

        User user = createTestUser();

        UserAccountConfirmation accountConfirmation =
                new UserAccountConfirmation(user);

        when(confirmationService.getVerificationToken(token)).thenReturn(accountConfirmation);

        doNothing().when(userService).saveEnabledUser(user);

        doNothing().when(confirmationRepository).delete(accountConfirmation);

        boolean result = confirmationService.accountVerification(token);

        assertEquals(user.getEmail(), confirmationService.getVerificationToken(token).getUser().getEmail());

        assertTrue(result);
    }

    private static User createTestUser() {

        return User
                .builder()
                .fullName("Nikola")
                .email("nikola@test.bg")
                .password("test")
                .enabled(false)
                .roles(List.of(
                        new UserRole(UserRoleEnum.ADMIN),
                        new UserRole(UserRoleEnum.USER)
                ))
                .build();
    }
}