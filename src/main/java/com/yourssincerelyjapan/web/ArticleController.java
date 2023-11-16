package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.event.OnArticleChangeEvent;
import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final ApplicationEventPublisher eventPublisher;

    public ArticleController(ArticleService articleService, ApplicationEventPublisher eventPublisher) {

        this.articleService = articleService;
        this.eventPublisher = eventPublisher;
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
                                         RedirectAttributes redirectAttributes,
                                         @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("newArticleDTO", newArticleDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newArticleDTO", bindingResult);

            modelAndView.setViewName("redirect:/articles/new");

            return modelAndView;
        }

        final boolean isArticleCreated = this.articleService.createArticle(newArticleDTO, principal.getUsername());

        if (isArticleCreated) {

            modelAndView.setViewName("redirect:/");

        } else {

            modelAndView.setViewName("redirect:/articles/new");

        }

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editArticle(ModelAndView modelAndView,
                                    @PathVariable("id") Long id) {

        final GetArticleDTO articleDTO = this.articleService.getSingleArticle(id);

        modelAndView.addObject("articleDTO", articleDTO);

        modelAndView.setViewName("edit-article");

        return modelAndView;
    }

    @PatchMapping("/edit/{id}")
    public ModelAndView editArticle(ModelAndView modelAndView,
                                    @PathVariable("id") Long id,
                                    @Valid ArticleDTO articleDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("articleDTO", articleDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleDTO", bindingResult);

            modelAndView.setViewName("redirect:/articles/new");

            return modelAndView;
        }

        final boolean isArticleSaved = this.articleService.saveEditedArticle(id, articleDTO);

        if (isArticleSaved) {

            modelAndView.setViewName("redirect:/");

        } else {

            redirectAttributes.addFlashAttribute("badCredentials", true);

            modelAndView.setViewName(String.format("redirect:/articles/edit/%d", id));

        }

        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteArticle(ModelAndView modelAndView,
                                      @PathVariable("id") Long id) {

        boolean isDeleted = this.articleService.deleteArticle(id);

        if (isDeleted) {

            //TODO: Implement it in the service.
            // Tried with @Aspect - didn't work; Tried with publishing the event in the service - didn't work;
            //It works only from here (for now)
            this.eventPublisher.publishEvent(new OnArticleChangeEvent(this));

            modelAndView.setViewName("redirect:/");

        } else {

            modelAndView.setViewName(String.format("redirect:/articles/delete/%d", id));

        }

        return modelAndView;
    }
}
