package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserPostsController {

    @GetMapping("/posts")
    public ModelAndView userPosts(ModelAndView modelAndView) {

        modelAndView.setViewName("user-posts");

        return modelAndView;
    }
}
