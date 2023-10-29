package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;

public interface UserService {

    void administratorInit();

    boolean registerUser(UserRegistrationDTO userDTO);

    boolean verifyToken(String token);

}
