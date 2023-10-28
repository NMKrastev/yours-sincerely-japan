package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/japan")
public class HistoryController {

    @GetMapping("/history")
    public ModelAndView history(ModelAndView modelAndView) {

        modelAndView.setViewName("history");

        return modelAndView;
    }
}
