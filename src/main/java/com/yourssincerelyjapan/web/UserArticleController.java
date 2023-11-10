package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.service.ArticleService;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserArticleController {

    private final ArticleService articleService;

    public UserArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/{username}")
    public ModelAndView getUser(ModelAndView modelAndView,
                                @PathVariable("username") String username,
                                HttpSession session) {

        session.setAttribute("username", username);

        modelAndView.setViewName("redirect:/users/articles");

        return modelAndView;
    }

    @GetMapping("/articles")
    public ModelAndView getUserArticles(ModelAndView modelAndView,
                                        @SessionAttribute(name = "username") String username,
                                        @PageableDefault(size = 6) Pageable pageable) {

        final Page<GetArticleDTO> userArticles =
                this.articleService.findUserArticles(pageable, username);

        modelAndView.addObject("userArticles", userArticles);

        modelAndView.setViewName("user-articles");

        return modelAndView;
    }
}
