package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.CommentDTO;
import com.yourssincerelyjapan.model.dto.index.GetCommentDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.Comment;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.model.mapper.CommentMapper;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.repository.CommentRepository;
import com.yourssincerelyjapan.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        commentService = new CommentServiceImpl(commentRepository, userRepository, articleRepository, commentMapper);
    }

    @Test
    void createComment() {

        CommentDTO commentDTO = new CommentDTO();
        Long articleId = 1L;
        UserDetails principal = mock(UserDetails.class);
        when(principal.getUsername()).thenReturn("user@example.com");

        User user = new User();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        Article article = new Article();
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        Comment comment = new Comment();
        when(commentMapper.commentDtoToComment(commentDTO)).thenReturn(comment);

        // Act
        boolean result = commentService.createComment(commentDTO, articleId, principal);

        // Assert
        assertTrue(result);
        assertNotNull(comment.getUser());
        assertNotNull(comment.getArticle());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void getArticleComments() {

        Long articleId = 1L;
        Pageable pageable = Pageable.unpaged();
        Page<Comment> commentPage = new PageImpl<>(Collections.singletonList(new Comment()));
        when(commentRepository.findByArticle_IdOrderByCreatedOnDesc(pageable, articleId)).thenReturn(commentPage);
        when(commentMapper.commentToGetCommentDto(any())).thenReturn(new GetCommentDTO());

        // Act
        Page<GetCommentDTO> result = commentService.getArticleComments(pageable, articleId);

        // Assert
        assertNotNull(result);
        assertEquals(commentPage.getTotalElements(), result.getTotalElements());
        verify(commentRepository, times(1)).findByArticle_IdOrderByCreatedOnDesc(pageable, articleId);
        verify(commentMapper, times(1)).commentToGetCommentDto(any());
    }

    @Test
    void deleteCommentsFromDeletedArticle() {

        List<Comment> comments = Collections.singletonList(new Comment());

        commentService.deleteCommentsFromDeletedArticle(comments);

        comments.forEach(c -> {
            assertNull(c.getArticle());
            assertNull(c.getUser());
        });
        verify(commentRepository, times(1)).saveAll(comments);
        verify(commentRepository, times(1)).deleteAll(comments);
    }

    @Test
    void editComment() {

        Long commentId = 1L;
        String commentContent = "Updated Comment";
        Comment comment = new Comment();
        comment.setArticle(createTestArticle());
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Long result = commentService.editComment(commentId, commentContent);

        assertEquals(comment.getArticle().getId(), result);
        assertEquals(commentContent, comment.getCommentContent());
        assertNotNull(comment.getModifiedOn());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void deleteComment() {

        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setArticle(createTestArticle());
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.deleteComment(commentId);

        assertNull(comment.getUser());
        assertNull(comment.getArticle());
        verify(commentRepository, times(1)).delete(comment);
    }

    private Article createTestArticle() {

        return Article.builder()
                .title("Test Title")
                .content("Test Content")
                .user(createTestUser())
                .categories(Collections.emptyList())
                .pictures(Collections.emptyList())
                .createdOn(LocalDateTime.now())
                .modifiedOn(LocalDateTime.now())
                .build();
    }

    private User createTestUser() {

        return User.builder()
                .email("test@example.com")
                .fullName("Test User")
                .roles(Collections.singletonList(new UserRole(UserRoleEnum.USER)))
                .enabled(true)
                .build();
    }
}