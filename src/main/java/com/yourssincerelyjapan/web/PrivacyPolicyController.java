package com.yourssincerelyjapan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrivacyPolicyController {

    @GetMapping("/privacy-policy")
    public ModelAndView getPrivacyPolicy(ModelAndView modelAndView) {

        modelAndView.setViewName("privacy-policy");

        return modelAndView;
    }
}
