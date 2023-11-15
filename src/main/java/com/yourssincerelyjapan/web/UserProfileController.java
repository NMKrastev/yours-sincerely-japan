package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                                       @SessionAttribute(name = "username") String username,
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

        final UserDTO userDTO = this.userService.getUserDtoByEmail(username);

        modelAndView.addObject("userDTO", userDTO);

        modelAndView.setViewName("user-profile");

        return modelAndView;
    }

    @PatchMapping("/profile/{username}/fullName")
    public ModelAndView updateFullName(ModelAndView modelAndView,
                                       @PathVariable("username") String username,
                                       @RequestParam("fullName") String fullName,
                                       HttpSession session) {

        final boolean isNameUpdated = this.userService.updateFullName(username, fullName);

        if (isNameUpdated) {

            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        } else {

            session.setAttribute("badNameData", true);
            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        }

        return modelAndView;
    }

    @PatchMapping("/profile/{username}/email")
    public ModelAndView updateEmail(ModelAndView modelAndView,
                                    @PathVariable("username") String username,
                                    @RequestParam("email") String email,
                                    HttpSession session) {

        final boolean isEmailUpdated = this.userService.updateEmail(username, email);

        if (isEmailUpdated) {

            this.userService.logoutUser();

            modelAndView.setViewName("redirect:/users/login");

            return modelAndView;

        } else {

            session.setAttribute("badEmailData", true);
            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        }

        return modelAndView;
    }

    @PatchMapping("/profile/{username}/profilePicture")
    public ModelAndView updateProfilePicture(ModelAndView modelAndView,
                                             @PathVariable("username") String username,
                                             @RequestParam("profilePicture") MultipartFile profilePicture,
                                             HttpSession session) {

        final boolean isProfilePictureUpdated = this.userService.updateProfilePicture(username, profilePicture);

        if (isProfilePictureUpdated) {

            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        } else {

            session.setAttribute("badProfilePictureData", true);
            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        }

        return modelAndView;
    }

    @PatchMapping("/profile/{username}/password")
    public ModelAndView updatePassword(ModelAndView modelAndView,
                                       @PathVariable("username") String username,
                                       @RequestParam("password") String password,
                                       HttpSession session) {

        final boolean isPasswordUpdated = this.userService.updatePassword(username, password);

        if (isPasswordUpdated) {

            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        } else {

            session.setAttribute("badPasswordData", true);
            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        }

        return modelAndView;
    }

    @DeleteMapping("/profile/{username}/deleteProfilePicture/{id}")
    public ModelAndView deleteProfilePicture(ModelAndView modelAndView,
                                             @PathVariable("username") String username,
                                             @PathVariable("id") Long id,
                                             HttpSession session) {

        boolean isProfilePictureDeleted = this.userService.deleteProfilePicture(username, id);

        if (isProfilePictureDeleted) {

            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        } else {

            session.setAttribute("badProfilePictureDelete", true);
            modelAndView.setViewName(String.format("redirect:/users/profile/%s", username));

        }

        return modelAndView;
    }
}
