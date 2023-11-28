package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryNameDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.model.mapper.ArticleMapper;
import com.yourssincerelyjapan.model.mapper.CategoryMapper;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryService =
                new CategoryServiceImpl(categoryRepository, articleRepository, categoryMapper, articleMapper);
    }

    @Test
    void findCategoryByName() {

        String categoryName = "FOOD";
        CategoryEnum categoryEnum = CategoryEnum.valueOf(categoryName.toUpperCase());
        Category expectedCategory = new Category();
        when(categoryRepository.findByName(categoryEnum)).thenReturn(expectedCategory);

        Category result = categoryService.findCategoryByName(categoryName);

        assertEquals(expectedCategory, result);
        verify(categoryRepository, times(1)).findByName(categoryEnum);
    }

    @Test
    void saveCategories() {

        List<Category> selectedCategories = Collections.singletonList(new Category());

        categoryService.saveCategories(selectedCategories);

        verify(categoryRepository, times(1)).saveAll(selectedCategories);
    }

    @Test
    void findFiveLatestCategoriesWithArticles() {

        List<Category> categories = Collections.singletonList(new Category(CategoryEnum.FOOD, null, List.of()));
        when(categoryRepository.findFiveLatestCategories()).thenReturn(categories);
        when(categoryMapper.categoryToGetCategoryDto(any())).thenReturn(new GetCategoryDTO());

        // Act
        List<GetCategoryDTO> result = categoryService.findFiveLatestCategoriesWithArticles();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoryRepository, times(1)).findFiveLatestCategories();
        verify(categoryMapper, times(1)).categoryToGetCategoryDto(any());
    }

    @Test
    void findAllCategoriesByName() {

        List<Category> categories = Collections.singletonList(new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.categoryToGetCategoryNameDto(any())).thenReturn(new GetCategoryNameDTO());

        List<GetCategoryNameDTO> result = categoryService.findAllCategoriesByName();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).categoryToGetCategoryNameDto(any());
    }

    @Test
    void getSingleCategoryWithAllArticles() {

        String categoryName = "FOOD";
        Category category = new Category();
        when(categoryService.findCategoryByName(categoryName)).thenReturn(category);

        Pageable pageable = Pageable.unpaged();
        Page<Article> articlePage = new PageImpl<>(List.of(createTestArticle()));
        when(articleRepository.findArticleByCategoriesOrderByCreatedOnDesc(pageable, category)).thenReturn(articlePage);
        when(articleMapper.articleToGetArticleDto(any())).thenReturn(new GetArticleDTO());

        Page<GetArticleDTO> result = categoryService.getSingleCategoryWithAllArticles(pageable, categoryName);

        assertNotNull(result);
        assertEquals(articlePage.getTotalElements(), result.getTotalElements());
    }

    @Test
    void getAllCategories() {

        Pageable pageable = Pageable.unpaged();
        List<Category> category = getCategory();
        Page<Category> categoryPage = new PageImpl<>(category);
        when(categoryRepository.findAllByOrderByName(pageable)).thenReturn(categoryPage);
        when(categoryMapper.categoryToGetCategoryDto(any())).thenReturn(new GetCategoryDTO());

        Page<GetCategoryDTO> result = categoryService.getAllCategories(pageable);

        assertNotNull(result);
        assertEquals(categoryPage.getTotalElements(), result.getTotalElements());
        verify(categoryRepository, times(1)).findAllByOrderByName(pageable);
        verify(categoryMapper, times(1)).categoryToGetCategoryDto(any());
    }

    @Test
    void getSelectedCategories() {

        List<String> selected = Collections.singletonList("FOOD");
        Category expectedCategory = new Category(CategoryEnum.FOOD,
                                                LocalDateTime.now(),
                                                List.of(createTestArticle()));
        when(categoryService.findCategoryByName("FOOD")).thenReturn(expectedCategory);

        List<Category> result = categoryService.getSelectedCategories(selected);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedCategory, result.get(0));
    }

    @Test
    void checkForCategoriesWithoutArticles() {

        Category category = new Category(CategoryEnum.FOOD,
                LocalDateTime.now(),
                List.of(createTestArticle()));
        List<Category> categories = Collections.singletonList(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        categoryService.checkForCategoriesWithoutArticles();

        assertNotNull(category.getLatestCreatedArticle());
        verify(categoryRepository, times(1)).saveAll(categories);
    }

    private List<Category> getCategory() {
        return List.of(new Category(
                CategoryEnum.FOOD,
                LocalDateTime.now(),
                List.of(createTestArticle())));
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