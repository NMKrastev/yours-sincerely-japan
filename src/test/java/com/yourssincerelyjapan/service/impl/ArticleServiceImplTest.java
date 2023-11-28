package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.event.OnArticleDeletionEvent;
import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.*;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.model.mapper.ArticleMapper;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.service.ArticlePictureService;
import com.yourssincerelyjapan.service.ArticleService;
import com.yourssincerelyjapan.service.CategoryService;
import com.yourssincerelyjapan.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ArticlePictureService articlePictureService;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        articleService = new ArticleServiceImpl(
                articleRepository, userService, categoryService,
                articlePictureService, articleMapper, eventPublisher);
    }

    @Test
    void createArticle() {

        ArticleDTO articleDTO = createTestArticleDTO();
        User user = createTestUser();

        Article newArticle = createTestArticle();
        when(articleMapper.newArticleDtoToArticle(articleDTO)).thenReturn(newArticle);
        when(categoryService.getSelectedCategories(articleDTO.getSelected())).thenReturn(Collections.emptyList());
        when(articlePictureService.saveArticlePictures(articleDTO.getUploadImages())).thenReturn(Collections.emptyList());
        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(createTestUser()));
        when(articleRepository.save(any(Article.class))).thenReturn(newArticle);

        boolean result = !articleService.createArticle(articleDTO, user.getEmail());

        assertTrue(result);
        verify(categoryService, times(1)).saveCategories(anyList());
        verify(articleRepository, times(1)).findById(newArticle.getId());
    }

    @Test
    void getSingleArticle() {

        Article article = createTestArticle();
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));
        when(articleMapper.articleToGetArticleDto(article)).thenReturn(createTestGetArticleDTO());

        GetArticleDTO result = articleService.getSingleArticle(1L);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Content", result.getContent());
    }

    @Test
    void findUserArticles() {

        Pageable pageable = mock(Pageable.class);
        UserDetails ud = createUserDetails();


        User user = createTestUser();
        when(userService.findUserByEmail(ud.getUsername())).thenReturn(Optional.of(user));

        List<Article> articles = createTestArticles();
        Page<Article> articlePage = new PageImpl<>(articles, pageable, 1);
        when(articleRepository.findArticleByUserOrderByCreatedOnDesc(pageable, user)).thenReturn(articlePage);
        when(articleMapper.articleToGetArticleDto(any(Article.class))).thenReturn(createTestGetArticleDTO());

        Page<GetArticleDTO> result = articleService.findUserArticles(pageable, ud);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Title", result.getContent().get(0).getTitle());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return Collections.singletonList(authority);
    }

    @Test
    void findAllArticlesFromCategory() {

        Pageable pageable = mock(Pageable.class);
        Category category = createTestCategory();

        List<Article> articles = Collections.singletonList(createTestArticle());
        Page<Article> articlePage = new PageImpl<>(articles, pageable, 1);
        when(articleRepository.findArticleByCategoriesOrderByCreatedOnDesc(pageable, category)).thenReturn(articlePage);
        when(articleMapper.articleToGetArticleDto(any(Article.class))).thenReturn(createTestGetArticleDTO());

        Page<GetArticleDTO> result = articleService.findAllArticlesFromCategory(pageable, category);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Title", result.getContent().get(0).getTitle());
    }

    @Test
    void saveEditedArticle() {

        long articleId = 1L;
        Article article = createTestArticle();
        ArticleDTO articleDTO = createTestArticleDTO();
        UserDetails userDetails = createUserDetails();
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);

        boolean result = !articleService.saveEditedArticle(articleId, articleDTO, userDetails);

        assertTrue(result);
        verify(articleRepository, times(1)).findById(article.getId());
    }

    @Test
    void deleteArticle() {

        long articleId = 1L;
        Article article = createTestArticleWithComments();
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));

        boolean result = articleService.deleteArticle(articleId);

        assertTrue(result);
    }

    private ArticleDTO createTestArticleDTO() {

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle("Test Title");
        articleDTO.setContent("Test Content");
        articleDTO.setSelected(List.of("travel", "cars"));
        articleDTO.setUploadImages(Collections.emptyList());
        return articleDTO;
    }

    private UserDetails createUserDetails() {

        return new org.springframework.security.core.userdetails.User(
                "test@example.com",
                "password",
                getAuthorities());
    }

    private List<ArticlePicture> getArticlePictures() {

        return List.of(ArticlePicture
                .builder()
                .name("test")
                .type("image/jpeg")
                .imageData("dawdawdawd")
                .build());
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

    private Article createTestArticleWithComments() {

        return Article.builder()
                .title("Test Title")
                .content("Test Content")
                .user(createTestUser())
                .categories(Collections.emptyList())
                .pictures(Collections.emptyList())
                .createdOn(LocalDateTime.now())
                .modifiedOn(LocalDateTime.now())
                .comments(List.of(new Comment("Content", LocalDateTime.now(), null, createTestUser(), createTestArticle())))
                .build();
    }

    private List<Article> createTestArticles() {

        return List.of(Article.builder()
                .title("Test Title")
                .content("Test Content")
                .user(createTestUser())
                .categories(Collections.emptyList())
                .pictures(Collections.emptyList())
                .createdOn(LocalDateTime.now())
                .modifiedOn(LocalDateTime.now())
                .build());
    }

    private GetArticleDTO createTestGetArticleDTO() {

        GetArticleDTO getArticleDTO = new GetArticleDTO();
        getArticleDTO.setTitle("Test Title");
        getArticleDTO.setContent("Test Content");
        return getArticleDTO;
    }

    private User createTestUser() {

        return User.builder()
                .email("test@example.com")
                .fullName("Test User")
                .roles(Collections.singletonList(new UserRole(UserRoleEnum.USER)))
                .enabled(true)
                .build();
    }

    private Category createTestCategory() {

        return new Category(
                CategoryEnum.FOOD,
                LocalDateTime.now(),
                List.of(createTestArticle()));

    }

}