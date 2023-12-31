package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.ReCaptchaResponseDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.service.ReCaptchaService;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import com.yourssincerelyjapan.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;
    private final UserAccountConfirmationService confirmationService;
    private final ReCaptchaService reCaptchaService;

    public UserRegistrationController(UserService userService, UserAccountConfirmationService confirmationService,
                                      ReCaptchaService reCaptchaService) {

        this.userService = userService;
        this.confirmationService = confirmationService;
        this.reCaptchaService = reCaptchaService;
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
                                         @RequestParam("g-recaptcha-response") String reCaptchaResponse) throws UnsupportedEncodingException {

        boolean isBot = !this.reCaptchaService.verify(reCaptchaResponse)
                .map(ReCaptchaResponseDTO::isSuccess)
                .orElse(false);

        if (isBot) {

            modelAndView.setViewName("redirect:/");

            return modelAndView;
        }

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);

            modelAndView.setViewName("redirect:/users/registration");

            return modelAndView;
        }

        boolean isUserRegistered = this.userService.registerUser(userRegistrationDTO);

        if (isUserRegistered) {

            modelAndView.setViewName("redirect:/users/login?isRegistered=true");

        } else {

            modelAndView.setViewName("redirect:/users/registration");

        }

        return modelAndView;
    }

    @GetMapping("/account-verification")
    public ModelAndView confirmRegistration(ModelAndView modelAndView,
                                            @RequestParam("token") String token) {

        boolean isVerificationSuccess = this.confirmationService.accountVerification(token);

        if (!isVerificationSuccess) {

            modelAndView.setViewName("redirect:/users/login?isInvalidToken=true");

            return modelAndView;
        }

        modelAndView.setViewName("redirect:/users/login");

        return modelAndView;

    }
}
