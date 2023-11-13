package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.utils.LocalDateTimeMapper;
import com.yourssincerelyjapan.utils.LocalDateTimeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LocalDateTimeMapper.class)
public interface ArticleMapper {

    @Mapping(target = "createdOn", qualifiedBy = LocalDateTimeMapping.class)
    Article newArticleDtoToArticle(ArticleDTO newArticleDTO);

    GetArticleDTO articleToGetArticleDto(Article article);
}
