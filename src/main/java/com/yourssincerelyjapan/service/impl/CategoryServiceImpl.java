package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryNameDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import com.yourssincerelyjapan.model.mapper.CategoryMapper;
import com.yourssincerelyjapan.repository.CategoryRepository;
import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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
    public List<GetCategoryDTO> findFiveLatestCategoriesWithArticles() {

        return this.categoryRepository
                .findFiveLatestCategories()
                .stream()
                .peek(category -> {

                    final List<Article> articles = category
                            .getArticles()
                            .stream()
                            .sorted(Comparator.comparing(Article::getCreatedOn).reversed())
                            .limit(5)
                            .toList();

                    category.setArticles(articles);

                })
                .map(this.categoryMapper::categoryToGetCategoryDto)
                .toList();

        /*for (GetCategoryDTO category : categories) {
            List<GetArticleDTO> articles = category.getArticles()
                    .stream()
                    .sorted(Comparator.comparing(GetArticleDTO::getCreatedOn).reversed())
                    .limit(5)
                    .toList();

            category.setArticles(articles);
        }*/

//        return categories;
    }

    @Override
    public List<GetCategoryNameDTO> findAllCategories() {

        return this.categoryRepository
                .findAll()
                .stream()
                .map(this.categoryMapper::categoryToGetCategoryNameDto)
                .toList();
    }
}
