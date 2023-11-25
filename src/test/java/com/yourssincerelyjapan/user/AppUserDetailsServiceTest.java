package com.yourssincerelyjapan.user;

import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {

    private AppUserDetailsService userDetailsServiceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        userDetailsServiceToTest = new AppUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserNotFound() {

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsServiceToTest.loadUserByUsername("johndoe@test.bg"));
    }

    @Test
    void testUserFoundException() {

        User userTest = createTestUser();

        when(mockUserRepository.findByEmailIgnoreCase("nikola@test.bg"))
                .thenReturn(Optional.of(userTest));

        UserDetails userDetails = userDetailsServiceToTest.loadUserByUsername(userTest.getEmail());

        assertNotNull(userDetails);

        assertEquals(userTest.getEmail(), userDetails.getUsername(),
                "Username is not mapped to email!");

        assertEquals(userTest.getPassword(), userDetails.getPassword());

        assertEquals(2, userDetails.getAuthorities().size());

        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN),
                "The user is not admin!");

        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER),
                "The user is not user!");
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {

        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(expectedAuthority));
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