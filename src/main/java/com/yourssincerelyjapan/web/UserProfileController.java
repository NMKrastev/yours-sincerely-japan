package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PatchMapping("/profile/{username}/fullName")
    public ModelAndView updateFullName(ModelAndView modelAndView,
                                       @PathVariable("username") String username,
                                       @RequestParam("fullName") String fullName) {

        //final boolean isUpdated = this.userService.updateFullName(fullName, username);

        return modelAndView;
    }

    @PatchMapping("/profile/{username}/email")
    public ModelAndView updateEmail(ModelAndView modelAndView,
                                       @PathVariable("username") String username,
                                       @RequestParam("email") String email) {

        //final boolean isUpdated = this.userService.updateFullName(fullName, username);

        return modelAndView;
    }

    @PutMapping("/profile/{username}/profilePicture")
    public ModelAndView updateProfilePicture(ModelAndView modelAndView,
                                    @PathVariable("username") String username,
                                    @RequestParam("profilePicture") MultipartFile profilePicture) {

        //final boolean isUpdated = this.userService.updateFullName(fullName, username);

        return modelAndView;
    }

    @PatchMapping("/profile/{username}/password")
    public ModelAndView updatePassword(ModelAndView modelAndView,
                                    @PathVariable("username") String username,
                                    @RequestParam("password") String password) {

        //final boolean isUpdated = this.userService.updateFullName(fullName, username);

        return modelAndView;
    }

    @DeleteMapping("/profile/{username}/deleteProfilePicture/{id}")
    public ModelAndView deleteProfilePicture(ModelAndView modelAndView,
                                             @PathVariable("username") String username,
                                             @PathVariable("id") Long id) {


        return modelAndView;
    }

}
