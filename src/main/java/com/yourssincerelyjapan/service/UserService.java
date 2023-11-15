package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    void administratorInit();

    boolean registerUser(UserRegistrationDTO userDTO) throws UnsupportedEncodingException;

    void saveEnabledUser(User user);

    List<UserDTO> getAllUsers();

    UserDTO findUser(Long id);

    User findUserByEmail(String username);

    UserDTO getUserDtoByEmail(String username);

    boolean updateFullName(String username, String fullName);

    boolean updateEmail(String username, String email);

    boolean updateProfilePicture(String username, MultipartFile profilePicture);

    boolean deleteProfilePicture(String username, Long id);

    boolean updatePassword(String username, String password);

    void logoutUser();
}
