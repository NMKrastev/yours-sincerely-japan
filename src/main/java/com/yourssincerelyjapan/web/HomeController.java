package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.index.GetCategoryDTO;
import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {

        final List<GetCategoryDTO> categories =
                this.categoryService.findFiveLatestCategoriesWithArticles();

        modelAndView.addObject("categories", categories);

        modelAndView.setViewName("index");

        return modelAndView;
    }
}
