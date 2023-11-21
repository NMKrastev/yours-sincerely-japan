package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {

    @GetMapping("/about")
    public ModelAndView getAboutPage(ModelAndView modelAndView) {

        modelAndView.setViewName("about");

        return modelAndView;
    }
}
