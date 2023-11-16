package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface ArticleService {

    boolean createArticle(ArticleDTO newArticleDTO, String username);

    GetArticleDTO getSingleArticle(Long id);

    Page<GetArticleDTO> findUserArticles(Pageable pageable, UserDetails principal);

    Page<GetArticleDTO> findAllArticlesFromCategory(Pageable pageable, Category category);

    boolean saveEditedArticle(Long id, ArticleDTO articleDTO, UserDetails principal);

    boolean deleteArticle(Long id);

}
