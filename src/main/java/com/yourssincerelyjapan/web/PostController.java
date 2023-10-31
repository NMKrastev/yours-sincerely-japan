package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @PostMapping("/new")
    public ModelAndView newPost(ModelAndView modelAndView, Article article, @RequestParam("selected") List<String> selected) {



        return modelAndView;

    }
}
