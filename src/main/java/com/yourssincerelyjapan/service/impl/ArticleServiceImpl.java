package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.NewArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.ArticlePicture;
import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.mapper.ArticleMapper;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.service.ArticlePictureService;
import com.yourssincerelyjapan.service.ArticleService;
import com.yourssincerelyjapan.service.CategoryService;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ArticlePictureService articlePictureService;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService,
                              CategoryService categoryService, ArticlePictureService articlePictureService,
                              ArticleMapper articleMapper) {

        this.articleRepository = articleRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.articlePictureService = articlePictureService;
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean createArticle(NewArticleDTO newArticleDTO) {

        final Article newArticle = this.articleMapper.newArticleDtoToArticle(newArticleDTO);

        final List<Category> selectedCategories;

        if (!newArticleDTO.getSelected().isEmpty()) {

            selectedCategories = newArticleDTO
                    .getSelected()
                    .stream()
                    .map(this.categoryService::findCategoryByName)
                    .toList();

        } else {
            return false;
        }

        newArticle.setCategories(selectedCategories);

        final List<ArticlePicture> savedArticlePictures =
                this.articlePictureService.saveArticlePictures(newArticleDTO.getUploadImages());
        newArticle.setPictures(savedArticlePictures);

        final User articleOwner = this.userService.findUserByEmail(newArticleDTO.getUsername());
        newArticle.setUser(articleOwner);

        final Article savedArticle = this.articleRepository.save(newArticle);

        selectedCategories
                .forEach(e -> e.setLatestCreatedArticle(savedArticle.getCreatedOn()));

        this.categoryService.saveCategories(selectedCategories);

        return this.articleRepository.findById(savedArticle.getId()).isPresent();
    }

    @Override
    public GetArticleDTO getSingleArticle(Long id) {

        return this.articleMapper
                .articleToGetArticleDto(this.articleRepository
                        .findById(id)
                        .get());
    }

    @Override
    public Page<GetArticleDTO> findUserArticles(Pageable pageable, String username) {

        final User user = this.userService.findUserByEmail(username);

        final Page<Article> articlePage = this.articleRepository
                .findArticleByUserOrderByCreatedOnDesc(pageable, user);

        final List<GetArticleDTO> userArticles = articlePage
                .getContent()
                .stream()
                .map(this.articleMapper::articleToGetArticleDto)
                .toList();

        return new PageImpl<>(userArticles, pageable, articlePage.getTotalElements());
    }

    @Override
    public Page<GetArticleDTO> findAllArticlesFromCategory(Pageable pageable, Category category) {

        final Page<Article> allCategoryArticles =
                this.articleRepository.findArticleByCategoriesOrderByCreatedOnDesc(pageable, category);

        final List<GetArticleDTO> allArticles = allCategoryArticles
                .getContent()
                .stream()
                .map(this.articleMapper::articleToGetArticleDto)
                .toList();

        return new PageImpl<>(allArticles, pageable, allCategoryArticles.getTotalElements());
    }
}
