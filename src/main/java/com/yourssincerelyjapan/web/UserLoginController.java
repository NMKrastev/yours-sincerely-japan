package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    @GetMapping("/login")
    public ModelAndView userLogin(ModelAndView modelAndView) {

        modelAndView.setViewName("login");

        return modelAndView;
    }

    @PostMapping("/login-error")
    public ModelAndView onLoginFailure(ModelAndView modelAndView, @ModelAttribute("email") String email) {

        modelAndView.addObject("email", email);
        modelAndView.addObject("badCredentials", true);

        modelAndView.setViewName("login");

        return modelAndView;
    }
}
