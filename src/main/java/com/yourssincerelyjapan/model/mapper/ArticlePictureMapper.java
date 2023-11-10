package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.index.GetArticlePictureDTO;
import com.yourssincerelyjapan.model.entity.ArticlePicture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticlePictureMapper {

    GetArticlePictureDTO articlePictureToGetArticlePictureDto(ArticlePicture articlePicture);
}
