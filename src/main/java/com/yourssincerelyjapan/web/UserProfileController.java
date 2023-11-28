package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/profile")
    public ModelAndView getUserProfile(ModelAndView modelAndView,
                                       @AuthenticationPrincipal UserDetails principal,
                                       /*@SessionAttribute(name = "username") String username,*/
                                       @SessionAttribute(name = "badNameData", required = false) boolean badNameData,
                                       @SessionAttribute(name = "badEmailData", required = false) boolean badEmailData,
                                       @SessionAttribute(name = "badProfilePictureData", required = false) boolean badProfilePictureData,
                                       @SessionAttribute(name = "badProfilePictureDelete", required = false) boolean badProfilePictureDelete,
                                       @SessionAttribute(name = "badPasswordData", required = false) boolean badPasswordData,
                                       HttpSession session) {

        if (badNameData) {
            modelAndView.addObject("badNameData", true);
            session.removeAttribute("badNameData");
        }

        if (badEmailData) {
            modelAndView.addObject("badEmailData", true);
            session.removeAttribute("badEmailData");
        }

        if (badProfilePictureData) {
            modelAndView.addObject("badProfilePictureData", true);
            session.removeAttribute("badProfilePictureData");
        }

        if (badProfilePictureDelete) {
            modelAndView.addObject("badProfilePictureDelete", true);
            session.removeAttribute("badProfilePictureDelete");
        }

        if (badPasswordData) {
            modelAndView.addObject("badPasswordData", true);
            session.removeAttribute("badPasswordData");
        }

        final UserDTO userDTO = this.userService.getUserDtoByEmail(principal);

        modelAndView.addObject("userDTO", userDTO);

        modelAndView.setViewName("user-profile");

        return modelAndView;
    }

    @PatchMapping("/profile/fullName")
    public ModelAndView updateFullName(ModelAndView modelAndView,
                                       @AuthenticationPrincipal UserDetails principal,
                                       @RequestParam("fullName") String fullName,
                                       HttpSession session) {

        final boolean isNameUpdated = this.userService.updateFullName(principal, fullName);

        if (isNameUpdated) {

            modelAndView.setViewName("redirect:/users/profile");

        } else {

            session.setAttribute("badNameData", true);
            modelAndView.setViewName("redirect:/users/profile");

        }

        return modelAndView;
    }

    @PatchMapping("/profile/email")
    public ModelAndView updateEmail(ModelAndView modelAndView,
                                    @AuthenticationPrincipal UserDetails principal,
                                    @RequestParam("email") String email,
                                    HttpSession session) {

        final boolean isEmailUpdated = this.userService.updateEmail(principal, email);

        if (isEmailUpdated) {

            modelAndView.setViewName("redirect:/users/login");

            return modelAndView;

        } else {

            session.setAttribute("badEmailData", true);
            modelAndView.setViewName("redirect:/users/profile");

        }

        return modelAndView;
    }

    @PatchMapping("/profile/profilePicture")
    public ModelAndView updateProfilePicture(ModelAndView modelAndView,
                                             @AuthenticationPrincipal UserDetails principal,
                                             @RequestParam("profilePicture") MultipartFile profilePicture,
                                             HttpSession session) {

        final boolean isProfilePictureUpdated = this.userService.updateProfilePicture(principal, profilePicture);

        if (isProfilePictureUpdated) {

            modelAndView.setViewName("redirect:/users/profile");

        } else {

            session.setAttribute("badProfilePictureData", true);
            modelAndView.setViewName("redirect:/users/profile");

        }

        return modelAndView;
    }

    @PatchMapping("/profile/password")
    public ModelAndView updatePassword(ModelAndView modelAndView,
                                       @AuthenticationPrincipal UserDetails principal,
                                       @RequestParam("password") String password,
                                       HttpSession session) {

        final boolean isPasswordUpdated = this.userService.updatePassword(principal, password);

        if (isPasswordUpdated) {

            modelAndView.setViewName("redirect:/users/profile");

        } else {

            session.setAttribute("badPasswordData", true);
            modelAndView.setViewName("redirect:/users/profile");

        }

        return modelAndView;
    }

    @DeleteMapping("/profile/deleteProfilePicture")
    public ModelAndView deleteProfilePicture(ModelAndView modelAndView,
                                             @AuthenticationPrincipal UserDetails principal,
                                             HttpSession session) {

        boolean isProfilePictureDeleted = this.userService.deleteProfilePicture(principal);

        if (isProfilePictureDeleted) {

            modelAndView.setViewName("redirect:/users/profile");

        } else {

            session.setAttribute("badProfilePictureDelete", true);
            modelAndView.setViewName("redirect:/users/profile");

        }

        return modelAndView;
    }
}
