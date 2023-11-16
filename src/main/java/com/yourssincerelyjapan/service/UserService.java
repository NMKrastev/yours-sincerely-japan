package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void administratorInit();

    boolean registerUser(UserRegistrationDTO userDTO) throws UnsupportedEncodingException;

    void saveEnabledUser(User user);

    List<UserDTO> getAllUsers();

    UserDTO findUser(Long id);

    Optional<User> findUserByEmail(String username);

    UserDTO getUserDtoByEmail(UserDetails principal);

    boolean updateFullName(UserDetails principal, String fullName);

    boolean updateEmail(UserDetails principal, String email);

    boolean updateProfilePicture(UserDetails principal, MultipartFile profilePicture);

    boolean deleteProfilePicture(UserDetails principal);

    boolean updatePassword(UserDetails principal, String password);
}
