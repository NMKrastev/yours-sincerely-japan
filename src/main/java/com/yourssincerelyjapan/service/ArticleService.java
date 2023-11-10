package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.NewArticleDTO;
import com.yourssincerelyjapan.model.entity.Article;
import org.springframework.stereotype.Service;

public interface ArticleService {

    boolean createArticle(NewArticleDTO newArticleDTO);

    Article getSingleArticle(Long id);
}
