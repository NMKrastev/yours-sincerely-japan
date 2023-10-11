package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @GetMapping("/all")
    public ModelAndView allCategories(ModelAndView modelAndView) {

        modelAndView.setViewName("all-categories");

        return modelAndView;
    }
}
