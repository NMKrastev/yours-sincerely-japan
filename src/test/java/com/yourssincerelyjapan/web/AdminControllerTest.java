package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.service.AdminService;
import com.yourssincerelyjapan.service.UserRoleService;
import com.yourssincerelyjapan.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private AdminService adminService;
    @Mock
    private UserRoleService userRoleService;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(adminService, userService, userRoleService, eventPublisher))
                .build();
    }

    @Test
    void getAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/all"))
                .andExpect(status().isOk()) // Adjust status code as needed
                .andExpect(view().name("all-users")) // Adjust view name as needed
                .andExpect(model().attributeExists("allUsers")) // Ensure the model has the "allUsers" attribute
                .andReturn();
    }

    @Test
    void editUserGET() throws Exception {

        UserDTO userDTO = createUserDTO();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userDTOWithErrors", userDTO);

        when(userService.findUser(1L)).thenReturn(userDTO);
        when(userRoleService.rolesListToRolesMap(userDTO.getRoles())).thenReturn(Collections.emptyMap());

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/users/edit/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-user"))
                .andExpect(model().attributeExists("userDTO"))
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertEquals("edit-user", modelAndView.getViewName());
    }

/*    @Test
    void testEditUser_SuccessfulEdit() throws Exception {

        UserDTO userDTO = createUserDTO();

        MockHttpSession session = new MockHttpSession();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/admin/users/edit/1")
                .session(session)
                .param("selectedRoles", "1");

        when(adminService.saveEditedUser(userDTO, List.of(1L))).thenReturn(true);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/users/all"))
                .andExpect(redirectedUrl("/admin/users/all"))
                .andReturn();

    }*/

    @Test
    void editUserPATCH_WithErrors() throws Exception {

        UserDTO userDTO = createUserDTO();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userDTOWithErrors", userDTO);

        // Perform
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.patch("/admin/users/edit/1")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/users/edit/1"))
                .andExpect(redirectedUrl("/admin/users/edit/1"))
                .andReturn();


    }

    @Test
    void deleteUser_Success() throws Exception {

        when(adminService.deleteUser(anyLong())).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/admin/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/users/all"))
                .andExpect(redirectedUrl("/admin/users/all"))
                .andReturn();
    }

    @Test
    void deleteUser_Fail() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/admin/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/users/all"))
                .andExpect(redirectedUrl("/admin/users/all?userNotDeleted=true"))
                .andReturn();
    }

    private UserDTO createUserDTO() {

        return new UserDTO(
                1L,
                "test",
                "test@example.com",
                List.of(new UserRoleDTO(1L, "USER")),
                true,
                null);

    }
}