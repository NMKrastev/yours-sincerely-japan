package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.service.CategoryService;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

        List<Category> categories = this.categoryService.findFiveLatestCategoriesWithArticles();

        modelAndView.addObject("categories", categories);

        modelAndView.setViewName("index");

        return modelAndView;
    }
}
