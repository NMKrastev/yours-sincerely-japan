package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    boolean createArticle(ArticleDTO newArticleDTO);

    GetArticleDTO getSingleArticle(Long id);

    Page<GetArticleDTO> findUserArticles(Pageable pageable, String username);

    Page<GetArticleDTO> findAllArticlesFromCategory(Pageable pageable, Category category);
}
