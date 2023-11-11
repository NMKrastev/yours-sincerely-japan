package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    private final CategoryService categoryService;

    public GlobalController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute("allCategoriesNames")
    void initAllCategoriesNames(Model model) {
        model.addAttribute("allCategoriesNames", this.categoryService.findAllCategories());
    }
}
