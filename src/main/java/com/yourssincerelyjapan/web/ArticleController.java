package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.NewArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/single-article/{id}")
    public ModelAndView getSingleArticle(ModelAndView modelAndView,
                                         @PathVariable("id") Long id) {

        final GetArticleDTO article = this.articleService.getSingleArticle(id);

        modelAndView.addObject("article", article);

        modelAndView.setViewName("article");

        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView createNewArticle(ModelAndView modelAndView, @Valid NewArticleDTO newArticleDTO) {

        boolean isArticleCreated = this.articleService.createArticle(newArticleDTO);

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }


}
