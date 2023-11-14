package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryNameDTO;
import com.yourssincerelyjapan.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Category findCategoryByName(String name);

    void saveCategories(List<Category> selectedCategories);

    List<GetCategoryDTO> findFiveLatestCategoriesWithArticles();

    List<GetCategoryNameDTO> findAllCategoriesByName();

    Page<GetArticleDTO> getSingleCategoryWithAllArticles(Pageable pageable, String categoryName);

    Page<GetCategoryDTO> getAllCategories(Pageable pageable);

    List<Category> getSelectedCategories(List<String> selected);

    void checkForCategoriesWithoutArticles();
}
