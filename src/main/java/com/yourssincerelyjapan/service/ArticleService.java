package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.NewArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService {

    boolean createArticle(NewArticleDTO newArticleDTO);

    Article getSingleArticle(Long id);

    List<GetArticleDTO> findUserArticles(String username);
}
