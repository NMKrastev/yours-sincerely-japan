package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.entity.Category;

public interface CategoryService {

    Category findCategoryByName(String name);
}
