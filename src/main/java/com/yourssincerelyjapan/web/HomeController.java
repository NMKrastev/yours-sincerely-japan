package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.service.CategoryService;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final UserService userService;
    private final CategoryService categoryService;

    public HomeController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {

        UserDTO userDTO = this.userService.findUser(1L);

        Category travel = this.categoryService.findCategoryByName("CARS");

        modelAndView.addObject("userDTO", userDTO);
        modelAndView.addObject("category", travel);

        modelAndView.setViewName("index");

        return modelAndView;
    }
}
