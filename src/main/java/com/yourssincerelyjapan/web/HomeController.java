package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {

        User userDTO = this.userService.findUserUser(1L);

        modelAndView.addObject("userDTO", userDTO);

        modelAndView.setViewName("index");

        return modelAndView;
    }
}
