package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface UserService {

    void administratorInit();

    boolean registerUser(UserRegistrationDTO userDTO, HttpServletRequest request) throws UnsupportedEncodingException;

    boolean verifyToken(String token);

    void saveEnabledUser(User user);
}
