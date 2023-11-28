package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.AdminConfiguration;
import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserProfilePicture;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.model.mapper.UserMapper;
import com.yourssincerelyjapan.repository.UserRepository;
import com.yourssincerelyjapan.repository.UserRoleRepository;
import com.yourssincerelyjapan.service.ProfilePictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private ProfilePictureService pictureService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private AdminConfiguration adminConfiguration;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userService =
                new UserServiceImpl(userRepository,
                        userRoleRepository,
                        pictureService,
                        userMapper,
                        adminConfiguration,
                        passwordEncoder,
                        eventPublisher);
    }

    @Test
    void administratorInit() {

        when(userRoleRepository.findByName(UserRoleEnum.USER)).thenReturn(new ArrayList<>());
        when(userRepository.count()).thenReturn(0L);
        when(adminConfiguration.getFullName()).thenReturn("Admin");
        when(adminConfiguration.getEmail()).thenReturn("admin@example.com");
        when(adminConfiguration.getPassword()).thenReturn("admin_password");
        when(passwordEncoder.encode("admin_password")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.administratorInit();

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser() throws IOException {

        UserRegistrationDTO userDTO = createUserRegistrationDTO();

        UserProfilePicture profilePicture = getProfilePicture();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encoded_password");
        when(pictureService.saveProfilePicture(any(MultipartFile.class))).thenReturn(profilePicture);
        when(userMapper.userRegistrationDtoToUserEntity(userDTO)).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = userService.registerUser(userDTO);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveEnabledUser() {

        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.saveEnabledUser(new User());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers() {

        when(userRepository.findAll()).thenReturn(List.of(new User()));

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(userMapper.userToUserDto(any(User.class))).thenReturn(new UserDTO());

        UserDTO result = userService.findUser(1L);

        assertNotNull(result);
    }

    @Test
    void findUserByEmail() {

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        Optional<User> result = userService.findUserByEmail("test@example.com");

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    void getUserDtoByEmail() {

        UserDetails principal = createUserDetails();
        User user = createTestUser();
        UserDTO userDTO = createUserDTO();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserDtoByEmail(principal);

        assertNotNull(result);
    }

    @Test
    void updateFullName() {

        UserDetails principal = createUserDetails();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = !userService.updateFullName(principal, "New Full Name");

        assertTrue(result);
    }

    @Test
    void updateEmail() {

        UserDetails principal = createUserDetails();
        User user = createTestUser();

        when(userRepository.findByEmail("new-email@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByEmail(principal.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = !userService.updateEmail(principal, "new-email@example.com");

        assertTrue(result);
    }

    @Test
    void updateProfilePicture() throws IOException {

        UserDetails principal = createUserDetails();
        MultipartFile profilePicture = getMockMultipartFile();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        when(pictureService.saveProfilePicture(any(MultipartFile.class))).thenReturn(new UserProfilePicture());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = !userService.updateProfilePicture(principal, profilePicture);

        assertTrue(result);
    }

    @Test
    void deleteProfilePicture() {

        UserDetails principal = createUserDetails();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        doNothing().when(pictureService).deleteProfilePicture(any(UserProfilePicture.class));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = !userService.deleteProfilePicture(principal);

        assertTrue(result);
    }

    @Test
    void updatePassword() {

        UserDetails principal = createUserDetails();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(passwordEncoder.encode("Qwerty1@")).thenReturn("encoded_password");

        boolean result = !userService.updatePassword(principal, "Qwerty1@");

        assertTrue(result);
    }

    private static UserProfilePicture getProfilePicture() {
        return UserProfilePicture.builder()
                .name("test.jpg")
                .type("image/jpeg")
                .imageDataBase64(Base64.getEncoder().encodeToString("Mock image content".getBytes()))
                .build();
    }

    private UserRegistrationDTO createUserRegistrationDTO() throws IOException {
        return UserRegistrationDTO
                .builder()
                .fullName("Test")
                .email("test@example.com")
                .password("password")
                .confirmPassword("password")
                .profilePicture(getMockMultipartFile())
                .build();
    }

    private static MockMultipartFile getMockMultipartFile() throws IOException {

        byte[] content = {1, 2, 3, 4, 5};

        return new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                content
        );
    }

    private User createTestUser() {

        return User.builder()
                .email("test@example.com")
                .fullName("Test User")
                .roles(Collections.singletonList(new UserRole(UserRoleEnum.USER)))
                .enabled(true)
                .build();
    }

    private UserDetails createUserDetails() {

        return new org.springframework.security.core.userdetails.User(
                "test@example.com",
                "password",
                getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return Collections.singletonList(authority);
    }

    private UserDTO createUserDTO() {

        return new UserDTO(
                1L,
                "test",
                "test@example.com",
                List.of(new UserRoleDTO(1L, "USER")),
                true,
                null);

    }

}