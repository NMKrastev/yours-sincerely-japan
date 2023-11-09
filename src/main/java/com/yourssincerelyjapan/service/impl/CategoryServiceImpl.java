package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import com.yourssincerelyjapan.repository.CategoryRepository;
import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.stereotype.Service;

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
}
