package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.NewArticleDTO;
import org.springframework.stereotype.Service;

public interface ArticleService {

    boolean createArticle(NewArticleDTO newArticleDTO);
}
