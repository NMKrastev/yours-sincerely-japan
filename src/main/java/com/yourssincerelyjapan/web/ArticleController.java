package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("newArticleDTO")
    public void initUserRegistrationDTO(Model model) {
        model.addAttribute("newArticleDTO", new ArticleDTO());
    }

    @GetMapping("/single-article/{id}")
    public ModelAndView getSingleArticle(ModelAndView modelAndView,
                                         @PathVariable("id") Long id) {

        final GetArticleDTO article = this.articleService.getSingleArticle(id);

        modelAndView.addObject("article", article);

        modelAndView.setViewName("article");

        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView createNewArticle(ModelAndView modelAndView) {

        modelAndView.setViewName("new-article");

        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView createNewArticle(ModelAndView modelAndView,
                                         @Valid ArticleDTO newArticleDTO,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("newArticleDTO", newArticleDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newArticleDTO", bindingResult);

            modelAndView.setViewName("redirect:/articles/new");

            return modelAndView;
        }

        boolean isArticleCreated = this.articleService.createArticle(newArticleDTO);

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editArticle(ModelAndView modelAndView,
                                    @PathVariable("id") Long id,
                                    @Valid ArticleDTO articleDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {


        return modelAndView;
    }

}
