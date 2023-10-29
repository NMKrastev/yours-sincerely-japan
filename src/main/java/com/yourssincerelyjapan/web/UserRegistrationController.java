package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {

        this.userService = userService;
    }

    @ModelAttribute("userRegistrationDTO")
    public void initUserRegistrationDTO(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
    }

    @GetMapping("/registration")
    public ModelAndView userRegistration(ModelAndView modelAndView) {

        modelAndView.setViewName("registration");

        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView userRegistration(ModelAndView modelAndView,
                                         @Valid UserRegistrationDTO userRegistrationDTO,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);

            modelAndView.setViewName("redirect:/users/registration");

            return modelAndView;
        }

        boolean isUserRegistered = this.userService.registerUser(userRegistrationDTO);

        if (isUserRegistered) {

            modelAndView.setViewName("redirect:/users/login");

        } else {

            modelAndView.setViewName("redirect:/users/registration");

        }

        return modelAndView;
    }

}
