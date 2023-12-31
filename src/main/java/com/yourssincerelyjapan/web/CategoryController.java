package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ModelAndView allCategories(ModelAndView modelAndView,
                                      @PageableDefault(size = 5) Pageable pageable) {

        final Page<GetCategoryDTO> allCategories = this.categoryService.getAllCategories(pageable);

        modelAndView.addObject("allCategories", allCategories);

        modelAndView.setViewName("all-categories");

        return modelAndView;
    }

    @GetMapping("/{category}")
    public ModelAndView category(ModelAndView modelAndView,
                                 @PageableDefault Pageable pageable,
                                 @PathVariable("category") String categoryName) {

        final Page<GetArticleDTO> allArticlesFromCategory =
                this.categoryService.getSingleCategoryWithAllArticles(pageable, categoryName);

        modelAndView.addObject("categoryName", categoryName);
        modelAndView.addObject("allArticlesFromCategory", allArticlesFromCategory);

        modelAndView.setViewName("category");

        return modelAndView;
    }
}
