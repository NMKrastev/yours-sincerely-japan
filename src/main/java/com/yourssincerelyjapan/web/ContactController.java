package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.ContactDTO;
import com.yourssincerelyjapan.service.EmailService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @ModelAttribute("contactDTO")
    public void initContactDtoModel(Model model) {
        model.addAttribute("contactDTO", new ContactDTO());
    }

    @GetMapping("/contacts")
    public ModelAndView getContactForm(ModelAndView modelAndView,
                                       @SessionAttribute(value = "fullName", required = false) String fullName,
                                       HttpSession session) {

        if (fullName != null) {
            modelAndView.addObject("fullName", fullName);
            session.removeAttribute("name");
        }

        modelAndView.setViewName("contacts");

        return modelAndView;
    }

    @PostMapping("/contacts")
    public ModelAndView createNewContactMessage(ModelAndView modelAndView,
                                                @Valid ContactDTO contactDTO,
                                                BindingResult bindingResult,
                                                RedirectAttributes redirectAttributes,
                                                HttpSession session) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("contactDTO", contactDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactDTO", bindingResult);

            modelAndView.setViewName("redirect:/contacts");

            return modelAndView;
        }

        this.emailService.sendSimpleEmail(contactDTO);

        modelAndView.setViewName("redirect:/contacts?emailSent=true");

        session.setAttribute("fullName", contactDTO.getFullName());

        return modelAndView;
    }
}
