package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @GetMapping("/new")
    public ModelAndView createNewPost(ModelAndView modelAndView) {

        modelAndView.setViewName("new-post");

        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView createNewPost(ModelAndView modelAndView, Article article, @RequestParam("uploadImages") List<MultipartFile> uploadImages) {

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }
}
