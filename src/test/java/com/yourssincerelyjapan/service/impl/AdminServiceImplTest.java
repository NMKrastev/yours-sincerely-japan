package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.entity.*;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.session.SessionRegistry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private ProfilePictureRepository profilePictureRepository;

    @Mock
    private ArticlePictureRepository articlePictureRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ConfirmationRepository confirmationRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private SessionRegistry sessionRegistry;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEditedUser_SuccessfulEdit() {

        UserDTO userDTO = createdUserDTO();

        List<Long> selectedRoles = new ArrayList<>();
        selectedRoles.add(1L);

        User existingUser = createdExistingUser();

        User savedUser = createSavedUser();

        UserRole userRole = new UserRole(UserRoleEnum.USER);

        when(this.userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(this.userRoleRepository.findById(1L)).thenReturn(Optional.of(userRole));
        when(this.userRepository.save(any())).thenReturn(savedUser);

        boolean result = !this.adminService.saveEditedUser(userDTO, selectedRoles);

        assertTrue(result);
        assertEquals("New Full Name", savedUser.getFullName());
        assertEquals("new.email@example.com", savedUser.getEmail());
        assertTrue(savedUser.isEnabled());
        assertEquals(1, savedUser.getRoles().size());
        assertEquals(userRole.getName(), savedUser.getRoles().get(0).getName());
        assertNotNull(savedUser.getModifiedOn());
    }

    @Test
    void deleteUser() {

        User user = createdExistingUser();

        List<Article> articles = getArticles();
        List<Comment> comments = getComments();
        UserProfilePicture userProfilePicture = getUserProfilePicture(user);

        user.setArticles(articles);
        user.setComments(comments);
        user.setProfilePicture(userProfilePicture);

        UserAccountConfirmation confirmation = new UserAccountConfirmation(user);

        when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(this.confirmationRepository.findByUser(user)).thenReturn(Optional.of(confirmation));
        doNothing().when(this.profilePictureRepository).delete(user.getProfilePicture());
        doNothing().when(this.articlePictureRepository).delete(any(ArticlePicture.class));
        doNothing().when(this.articleRepository).delete(any(Article.class));
        doNothing().when(this.commentRepository).deleteAll(Collections.singleton(any(Comment.class)));

        boolean result = !this.adminService.deleteUser(1L);

        assertTrue(result);
        verify(this.confirmationRepository).delete(confirmation);
        verify(this.profilePictureRepository).delete(user.getProfilePicture());
        verify(this.sessionRegistry).getAllPrincipals();
    }

    private List<Article> getArticles() {

        return List.of(Article
                .builder()
                .title("test")
                .content("test 123 123")
                .pictures(getArticlePicture())
                .build());
    }

    private List<Comment> getComments() {

        return List.of(
                Comment
                        .builder()
                        .commentContent("Test comment Content")
                        .createdOn(LocalDateTime.now())
                        .build()
        );
    }

    private List<ArticlePicture> getArticlePicture() {

        return List.of(ArticlePicture
                .builder()
                .name("test")
                .type("image/jpeg")
                .imageData("dawdawdawd")
                .build());
    }

    private static UserProfilePicture getUserProfilePicture(User user) {

        return new UserProfilePicture(
                "test",
                "image/jpeg",
                "dawdawdawdwad",
                user);
    }

    private UserDTO createdUserDTO() {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFullName("New Full Name");
        userDTO.setEmail("new.email@example.com");
        userDTO.setEnabled(true);

        return userDTO;
    }

    private User createdExistingUser() {

        return User
                .builder()
                .fullName("Old Full Name")
                .email("old.email@example.com")
                .roles(List.of(new UserRole(UserRoleEnum.USER)))
                .enabled(false)
                .build();
    }

    private User createSavedUser() {

        return User
                .builder()
                .fullName("New Full Name")
                .email("new.email@example.com")
                .enabled(true)
                .roles(List.of(new UserRole(UserRoleEnum.USER)))
                .modifiedOn(LocalDateTime.now())
                .build();
    }
}