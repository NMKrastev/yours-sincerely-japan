package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.service.AdminService;
import com.yourssincerelyjapan.service.UserRoleService;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@SessionAttributes("userDTO")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final UserRoleService userRoleService;

    public AdminController(AdminService adminService, UserService userService,
                           UserRoleService userRoleService) {

        this.adminService = adminService;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @ModelAttribute("allRoles")
    public void initUserRoles(Model model) {

        final List<UserRoleDTO> allRoles = this.userRoleService.getAllRoles();

        model.addAttribute("allRoles", allRoles);
    }

    @GetMapping("/all")
    public ModelAndView getAllUsers(ModelAndView modelAndView) {

        final List<UserDTO> allUsers = this.userService.getAllUsers();

        modelAndView.addObject("allUsers", allUsers);

        modelAndView.setViewName("all-users");

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editUser(ModelAndView modelAndView,
                                 @PathVariable("id") Long id,
                                 HttpSession session) {

        UserDTO userDTO = (UserDTO) session.getAttribute("userDTOWithErrors");
        final Object badCredentials = session.getAttribute("badCredentials");

        if (userDTO == null) {

            if (badCredentials == null) {

                userDTO = this.userService.findUser(id);
                modelAndView.addObject("userRoles", this.userRoleService.rolesListToRolesMap(userDTO.getRoles()));

            } else {

                userDTO = (UserDTO) session.getAttribute("userDTOEmailExist");
                modelAndView.addObject("userRoles", this.userRoleService.rolesListToRolesMap(userDTO.getRoles()));

                modelAndView.addObject("badCredentials", true);

                session.removeAttribute("badCredentials");
                session.removeAttribute("userDTOEmailExist");
            }
        } else {
            // Remove the userDTO from the session, as it's being displayed
            session.removeAttribute("userDTOWithErrors");
            session.removeAttribute("badCredentials");
            session.removeAttribute("userDTOEmailExist");
        }

        modelAndView.addObject("userDTO", userDTO);
        modelAndView.setViewName("edit-user");

        return modelAndView;
    }

    @PatchMapping("/edit/{id}")
    public ModelAndView editUser(ModelAndView modelAndView,
                                 @Valid UserDTO userDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(name = "selectedRoles", required = false) List<Long> selectedRoles,
                                 HttpSession session) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userDTO", userDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", bindingResult);

            session.setAttribute("userDTOWithErrors", userDTO);

            modelAndView.setViewName(String.format("redirect:/admin/users/edit/%d", userDTO.getId()));

            return modelAndView;
        }

        final boolean isUpdated = this.adminService.saveEditedUser(userDTO, selectedRoles);

        if (isUpdated) {

            modelAndView.setViewName("redirect:/admin/users/all");

        } else {

            session.setAttribute("userDTOEmailExist", userDTO);

            session.setAttribute("badCredentials", true);

            modelAndView.setViewName(String.format("redirect:/admin/users/edit/%d", userDTO.getId()));
        }

        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteUser(ModelAndView modelAndView,
                                   @PathVariable("id") Long id) {

        final boolean isDeleted = this.adminService.deleteUser(id);

        if (isDeleted) {

            modelAndView.setViewName("redirect:/admin/users/all");

        } else {

            modelAndView.addObject("userNotDeleted", true);

            modelAndView.setViewName("redirect:/admin/users/all");

        }

        return modelAndView;
    }
}
