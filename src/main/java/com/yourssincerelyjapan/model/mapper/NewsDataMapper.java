package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.FetchNewsDataDTO;
import com.yourssincerelyjapan.model.dto.NewsDataDTO;
import com.yourssincerelyjapan.model.entity.NewsData;
import com.yourssincerelyjapan.utils.StringToLocalDateTimeMapping;
import com.yourssincerelyjapan.utils.StringToLocalDateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = StringToLocalDateTimeMapper.class)
public interface NewsDataMapper {

    @Mapping(source = "article_id", target = "fetchArticleId")
    @Mapping(source = "link", target = "newsUrl")
    @Mapping(source = "image_url", target = "imageUrl")
    @Mapping(source = "pubDate", target = "createdOn", qualifiedBy = StringToLocalDateTimeMapping.class)
    NewsData newsDataDtoToNewsData(FetchNewsDataDTO newsDataDTO);

    NewsDataDTO newsDataToNewsDataDto(NewsData newsData);
}
