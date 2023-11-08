package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.entity.Article;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping("/single-article")
    public ModelAndView getSingleArticle(ModelAndView modelAndView) {

        modelAndView.setViewName("article");

        return modelAndView;
    }

   /* @GetMapping("/new")
    public ModelAndView createNewPost(ModelAndView modelAndView) {

        modelAndView.setViewName("new-article");

        return modelAndView;
    }*/

    @PostMapping("/new")
    public ModelAndView createNewPost(ModelAndView modelAndView,
                                      @Valid ArticleDTO articleDTO,
                                      @RequestParam("username") String username) {

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }
}
