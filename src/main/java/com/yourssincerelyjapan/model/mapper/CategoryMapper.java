package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryNameDTO;
import com.yourssincerelyjapan.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    GetCategoryDTO categoryToGetCategoryDto(Category category);

    GetCategoryNameDTO categoryToGetCategoryNameDto(Category category);
}
