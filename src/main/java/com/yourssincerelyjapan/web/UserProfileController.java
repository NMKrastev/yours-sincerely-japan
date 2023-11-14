package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/profile/{username}")
    public ModelAndView getUser(ModelAndView modelAndView,
                                @PathVariable("username") String username,
                                HttpSession session) {

        session.setAttribute("username", username);

        modelAndView.setViewName("redirect:/users/profile");

        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView getUserProfile(ModelAndView modelAndView,
                                       @SessionAttribute(name = "username") String username) {

        final UserDTO userDTO = this.userService.getUserDtoByEmail(username);

        modelAndView.addObject("userDTO", userDTO);

        modelAndView.setViewName("user-profile");

        return modelAndView;
    }
}
