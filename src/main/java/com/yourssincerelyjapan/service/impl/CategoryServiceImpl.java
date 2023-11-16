package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryNameDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import com.yourssincerelyjapan.model.mapper.ArticleMapper;
import com.yourssincerelyjapan.model.mapper.CategoryMapper;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.repository.CategoryRepository;
import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository,
                               CategoryMapper categoryMapper, ArticleMapper articleMapper) {

        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.categoryMapper = categoryMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public Category findCategoryByName(String name) {

        return this.categoryRepository
                .findByName(CategoryEnum.valueOf(name.toUpperCase()));
    }

    @Override
    public void saveCategories(List<Category> selectedCategories) {

        this.categoryRepository.saveAll(selectedCategories);

        //this.checkForCategoriesWithoutArticles();
    }

    @Override
    public List<GetCategoryDTO> findFiveLatestCategoriesWithArticles() {

        return this.categoryRepository
                .findFiveLatestCategories()
                .stream()
                .peek(category -> {

                    final List<Article> articles = category
                            .getArticles()
                            .stream()
                            .sorted(Comparator.comparing(Article::getCreatedOn).reversed())
                            .limit(5)
                            .toList();

                    category.setArticles(articles);

                })
                .map(this.categoryMapper::categoryToGetCategoryDto)
                .toList();

    }

    @Override
    public List<GetCategoryNameDTO> findAllCategoriesByName() {

        return this.categoryRepository
                .findAll()
                .stream()
                .map(this.categoryMapper::categoryToGetCategoryNameDto)
                .toList();
    }

    @Override
    public Page<GetArticleDTO> getSingleCategoryWithAllArticles(Pageable pageable, String categoryName) {

        final Category category = this.findCategoryByName(categoryName);

        final Page<Article> allCategoryArticles =
                this.articleRepository.findArticleByCategoriesOrderByCreatedOnDesc(pageable, category);

        final List<GetArticleDTO> allArticles = allCategoryArticles
                .getContent()
                .stream()
                .map(this.articleMapper::articleToGetArticleDto)
                .toList();

        return new PageImpl<>(allArticles, pageable, allCategoryArticles.getTotalElements());
    }

    @Override
    public Page<GetCategoryDTO> getAllCategories(Pageable pageable) {

        final Page<Category> categories = this.categoryRepository
                .findAllByOrderByName(pageable);

        categories
                .forEach(c -> {

                    List<Article> articles = c.getArticles()
                            .stream()
                            .sorted(Comparator.comparing(Article::getCreatedOn).reversed())
                            .limit(10)
                            .toList();

                    c.setArticles(articles);
                });

        final List<GetCategoryDTO> allCategories = categories
                .stream()
                .map(this.categoryMapper::categoryToGetCategoryDto)
                .toList();

        return new PageImpl<>(allCategories, pageable, categories.getTotalElements());
    }

    @Override
    public List<Category> getSelectedCategories(List<String> selected) {

        if (!selected.isEmpty()) {

            return selected
                        .stream()
                        .map(this::findCategoryByName)
                        .toList();
        } else {
            throw new IllegalArgumentException("Categories cannot be empty or null!");
        }
    }

    @Override
    public void checkForCategoriesWithoutArticles() {

        final List<Category> categories = this.categoryRepository.findAll();

        for (Category category : categories) {

            if (category.getArticles().isEmpty()) {
                category.setLatestCreatedArticle(null);
            } else {

                final Article article = category.getArticles()
                        .stream()
                        .sorted(Comparator.comparing(Article::getCreatedOn).reversed())
                        .toList()
                        .get(0);

                if (!article.getCreatedOn().toString().equals(category.getLatestCreatedArticle().toString())) {
                    category.setLatestCreatedArticle(article.getCreatedOn());
                }
            }
        }

        this.categoryRepository.saveAll(categories);
    }
}
