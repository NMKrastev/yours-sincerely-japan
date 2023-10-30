package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;

import java.io.UnsupportedEncodingException;

public interface UserService {

    void administratorInit();

    boolean registerUser(UserRegistrationDTO userDTO) throws UnsupportedEncodingException;

    boolean verifyToken(String token);

}
