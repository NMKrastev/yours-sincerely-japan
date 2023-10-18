package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories/article")
public class ArticleController {

    @GetMapping("/post")
    public ModelAndView getPost(ModelAndView modelAndView) {

        modelAndView.setViewName("post");

        return modelAndView;
    }
}
