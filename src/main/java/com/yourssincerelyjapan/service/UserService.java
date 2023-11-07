package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void administratorInit();

    boolean registerUser(UserRegistrationDTO userDTO) throws UnsupportedEncodingException;

    void saveEnabledUser(User user);

    List<UserDTO> getAllUsers();

    UserDTO findUser(Long id);

    boolean saveEditedUser(UserDTO userDTO, List<Long> selectedRoles);

    boolean deleteUser(Long id);

    //Just for test purpose
    //User findUserUser(Long id);
}
