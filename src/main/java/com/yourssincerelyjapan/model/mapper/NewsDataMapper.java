package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.NewsDataDTO;
import com.yourssincerelyjapan.model.entity.NewsData;
import com.yourssincerelyjapan.utils.StringToLocalDateTime;
import com.yourssincerelyjapan.utils.StringToLocalDateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = StringToLocalDateTimeMapper.class)
public interface NewsDataMapper {

    @Mapping(source = "link", target = "newsUrl")
    @Mapping(source = "image_url", target = "imageUrl")
    @Mapping(source = "pubDate", target = "createdOn", qualifiedBy = StringToLocalDateTime.class)
    NewsData newsDataDtoToNewsData(NewsDataDTO newsDataDTO);
}
