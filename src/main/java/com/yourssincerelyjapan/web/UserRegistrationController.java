package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;
    private final UserAccountConfirmationService confirmationService;

    public UserRegistrationController(UserService userService, UserAccountConfirmationService confirmationService) {

        this.userService = userService;
        this.confirmationService = confirmationService;
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
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request) throws UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);

            modelAndView.setViewName("redirect:/users/registration");

            return modelAndView;
        }

        boolean isUserRegistered = this.userService.registerUser(userRegistrationDTO, request);

        if (isUserRegistered) {

            //TODO: redirect the registered user to a page saying that he/she has to check email.
            modelAndView.setViewName("redirect:/users/login-info");

        } else {

            modelAndView.setViewName("redirect:/users/registration");

        }

        return modelAndView;
    }

    @GetMapping("/account-verification")
    public ModelAndView confirmRegistration(ModelAndView modelAndView,
                                            @RequestParam("token") String token,
                                            RedirectAttributes redirectAttributes) {

        boolean isVerificationSuccess = this.confirmationService.accountVerification(token);

        if (!isVerificationSuccess) {

            modelAndView.addObject("badToken", true);
            //TODO: create token-not-exist.html page;
            modelAndView.setViewName("redirect:/users/token-not-exist");

            return modelAndView;
        }

        redirectAttributes.addFlashAttribute("badToken", false);

        modelAndView.setViewName("redirect:/users/login");

        return modelAndView;

    }

}
