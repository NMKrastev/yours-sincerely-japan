package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @GetMapping("/contacts")
    public ModelAndView getContacts(ModelAndView modelAndView) {

        modelAndView.setViewName("contacts");

        return modelAndView;
    }
}
