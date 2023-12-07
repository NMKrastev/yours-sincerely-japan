package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.event.OnArticleChangeEvent;
import com.yourssincerelyjapan.model.dto.ArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetCommentDTO;
import com.yourssincerelyjapan.service.ArticleService;
import com.yourssincerelyjapan.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final ApplicationEventPublisher eventPublisher;

    public ArticleController(ArticleService articleService, CommentService commentService,
                             ApplicationEventPublisher eventPublisher) {

        this.articleService = articleService;
        this.commentService = commentService;
        this.eventPublisher = eventPublisher;
    }

    @ModelAttribute("newArticleDTO")
    public void initNewArticleDTO(Model model) {

        model.addAttribute("newArticleDTO", new ArticleDTO());
    }

    @GetMapping("/single-article/{id}")
    public ModelAndView getArticle(ModelAndView modelAndView,
                                   @PathVariable("id") Long id,
                                   HttpSession session) {

        session.setAttribute("articleId", id);

        modelAndView.setViewName("redirect:/articles/single-article");

        return modelAndView;
    }

    @GetMapping("/single-article")
    public ModelAndView getUserArticle(ModelAndView modelAndView,
                                       @SessionAttribute("articleId") Long id,
                                       @SessionAttribute(value = "badCommentContent", required = false) boolean badCommentContent,
                                       @SessionAttribute(value = "badCommentEditContent", required = false) boolean badCommentEditContent,
                                       @SessionAttribute(value = "commentId", required = false) Long commentId,
                                       @SessionAttribute(value = "badCommentDeletion", required = false) boolean badCommentDeletion,
                                       HttpSession session,
                                       @PageableDefault(size = 5) Pageable pageable) {

        if (badCommentContent) {

            modelAndView.addObject("badCommentContent", true);

            session.removeAttribute("badCommentContent");
        }

        if (badCommentEditContent) {

            modelAndView.addObject("badCommentEditContent", true);
            modelAndView.addObject("commentId", commentId);

            session.removeAttribute("badCommentEditContent");
            session.removeAttribute("commentId");
        }

        if (badCommentDeletion) {

            modelAndView.addObject("badCommentDeletion", true);

            session.removeAttribute("badCommentDeletion");
        }

        final GetArticleDTO article = this.articleService.getSingleArticle(id);
        final Page<GetCommentDTO> comments = this.commentService.getArticleComments(pageable, id);

        modelAndView.addObject("article", article);
        modelAndView.addObject("comments", comments);

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

            modelAndView.setViewName("redirect:/articles/new?articleNotCreated=true");

        }

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editArticle(ModelAndView modelAndView,
                                    @PathVariable("id") Long id,
                                    @SessionAttribute(value = "badCredentials", required = false) boolean badCredentials) {

        if (badCredentials) {

            modelAndView.addObject("badCredentials", true);

        }

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
                                    RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal UserDetails principal,
                                    HttpSession session) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("articleDTO", articleDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleDTO", bindingResult);

            modelAndView.setViewName("redirect:/articles/new");

            return modelAndView;
        }

        final boolean isArticleSaved = this.articleService.saveEditedArticle(id, articleDTO, principal);

        if (isArticleSaved) {

            modelAndView.setViewName(String.format("redirect:/articles/single-article/%d", id));

        } else {

            session.setAttribute("badCredentials", true);

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
            // I think this is due to the @Transactional in the delete method;
            //It works only from here (for now)
            this.eventPublisher.publishEvent(new OnArticleChangeEvent(this));

            modelAndView.setViewName("redirect:/");

        } else {

            modelAndView.setViewName(String.format("redirect:/articles/delete/%d", id));

        }

        return modelAndView;
    }
}
