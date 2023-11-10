package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.NewArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article newArticleDtoToArticle(NewArticleDTO newArticleDTO);

    GetArticleDTO articleToGetArticleDto(Article article);
}
