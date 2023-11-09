package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import com.yourssincerelyjapan.repository.CategoryRepository;
import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByName(String name) {

        return this.categoryRepository
                .findByName(CategoryEnum.valueOf(name.toUpperCase()));
    }

    @Override
    public void saveCategories(List<Category> selectedCategories) {

        this.categoryRepository.saveAll(selectedCategories);
    }

    @Override
    public List<Category> findFiveLatestCategoriesWithArticles() {

        return this.categoryRepository
                .findAllByAndLatestCreatedArticleNotNullOrderByLatestCreatedArticleDesc()
                .stream()
                .limit(5)
                .toList();
    }
}
