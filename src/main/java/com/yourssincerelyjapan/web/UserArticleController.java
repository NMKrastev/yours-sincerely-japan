package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserArticleController {

    private final ArticleService articleService;

    public UserArticleController(ArticleService articleService) {

        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ModelAndView getUserArticles(ModelAndView modelAndView,
                                        @AuthenticationPrincipal UserDetails principal,
                                        @PageableDefault(size = 6) Pageable pageable) {

        final Page<GetArticleDTO> userArticles =
                this.articleService.findUserArticles(pageable, principal);

        modelAndView.addObject("userArticles", userArticles);

        modelAndView.setViewName("user-articles");

        return modelAndView;
    }
}
