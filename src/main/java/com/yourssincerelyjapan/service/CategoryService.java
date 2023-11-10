package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.model.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findCategoryByName(String name);

    void saveCategories(List<Category> selectedCategories);

    List<GetCategoryDTO> findFiveLatestCategoriesWithArticles();
}
