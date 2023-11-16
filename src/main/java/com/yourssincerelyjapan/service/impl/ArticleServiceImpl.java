package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.event.OnArticleChangeEvent;
import com.yourssincerelyjapan.model.dto.ArticleDTO;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ArticlePictureService articlePictureService;
    private final ArticleMapper articleMapper;
    private final ApplicationEventPublisher eventPublisher;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService,
                              CategoryService categoryService, ArticlePictureService articlePictureService,
                              ArticleMapper articleMapper, ApplicationEventPublisher eventPublisher) {

        this.articleRepository = articleRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.articlePictureService = articlePictureService;
        this.articleMapper = articleMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean createArticle(ArticleDTO newArticleDTO, String username) {

        final Article newArticle = this.articleMapper.newArticleDtoToArticle(newArticleDTO);

        final List<Category> selectedCategories =
                this.categoryService.getSelectedCategories(newArticleDTO.getSelected());

        newArticle.setCategories(selectedCategories);

        final List<ArticlePicture> savedArticlePictures =
                this.articlePictureService.saveArticlePictures(newArticleDTO.getUploadImages());
        newArticle.setPictures(savedArticlePictures);

        final User articleOwner = this.userService
                .findUserByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s not found!", username)));
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

        final User user = this.userService
                .findUserByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s not found!", username)));

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

    @Override
    public boolean saveEditedArticle(Long id, ArticleDTO articleDTO) {

        final Article article = this.articleRepository.findById(id).get();

        final Article editedArticle = mapToEditedArticle(article, articleDTO);

        return this.articleRepository
                .findById(editedArticle.getId())
                .isPresent();
    }

    @Override
    @Transactional
    public boolean deleteArticle(Long id) {

        final Article article = this.articleRepository.findById(id).get();

        this.articlePictureService.deleteArticlePictures(article);

        this.articleRepository.delete(article);

        return this.articleRepository.findById(article.getId()).isEmpty();
    }

    private Article mapToEditedArticle(Article article, ArticleDTO articleDTO) {

        if (!article.getTitle().equals(articleDTO.getTitle())) {
            article.setTitle(articleDTO.getTitle());
        }

        if (!article.getContent().equals(articleDTO.getContent())) {
            article.setContent(articleDTO.getContent());
        }

        final List<ArticlePicture> savedArticlePictures =
                this.articlePictureService.saveArticlePictures(articleDTO.getUploadImages());
        article.getPictures().addAll(savedArticlePictures);

        final List<Category> selectedCategories =
                this.categoryService.getSelectedCategories(articleDTO.getSelected());

        article.setCategories(selectedCategories);

        article.setModifiedOn(LocalDateTime.now());

        final Article saved = this.articleRepository.save(article);

        selectedCategories
                .forEach(c -> c.setLatestCreatedArticle(saved.getCreatedOn()));

        this.categoryService.saveCategories(selectedCategories);

        this.eventPublisher.publishEvent(new OnArticleChangeEvent(this));

        return saved;
    }
}
