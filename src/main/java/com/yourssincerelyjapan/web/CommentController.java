package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.CommentDTO;
import com.yourssincerelyjapan.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/articles/single-article")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ModelAttribute("commentDTO")
    public void initCommentDtoModel(Model model) {
        model.addAttribute("commentDTO", new CommentDTO());
    }

    @PostMapping("/comment/{articleId}")
    public ModelAndView createComment(ModelAndView modelAndView,
                                      @PathVariable("articleId") Long articleId,
                                      @Valid CommentDTO commentDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @AuthenticationPrincipal UserDetails principal,
                                      HttpSession session) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("commentDTO", commentDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentDTO", bindingResult);

            session.setAttribute("badCommentContent", true);

            modelAndView.setViewName(String.format("redirect:/articles/single-article/%d", articleId));

            return modelAndView;
        }

        boolean isCommentCreated = this.commentService.createComment(commentDTO, articleId, principal);

        if (isCommentCreated) {

            modelAndView.setViewName(String.format("redirect:/articles/single-article/%d", articleId));

        } else {

            //TODO: implement logic if false;
        }

        return modelAndView;
    }
}
