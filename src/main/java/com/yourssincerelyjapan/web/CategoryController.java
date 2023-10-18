package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping("/all")
    public ModelAndView allCategories(ModelAndView modelAndView) {

        modelAndView.setViewName("all-categories");

        return modelAndView;
    }

    /*Could be categoryId or the categoryName*/
    @GetMapping("/{category}")
    public ModelAndView category(ModelAndView modelAndView,
                                 @PathVariable("category") String categoryName) {

//        Category category = this.categoryService.findByName(categoryName);

//        modelAndView.addObject("category", category);

        modelAndView.setViewName("category");


        return modelAndView;
    }
}
