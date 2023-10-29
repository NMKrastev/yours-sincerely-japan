package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.mapper.UserMapper;
import com.yourssincerelyjapan.repository.UserRepository;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public void administratorInit() {

    }

    @Override
    public boolean registerUser(UserRegistrationDTO userDTO) {

        if (this.userRepository.findByEmail(userDTO.getEmail()).isPresent()
                || !userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return false;
        }

        //Using MapStruct for mapping entities
        final User newUser = this.userMapper.userDtoToUserEntity(userDTO);
        //newUser.setRole(this.roleService.getUserRole());
        newUser.setCreatedOn(LocalDateTime.now());

        final User savedUser = this.userRepository.save(newUser);

        return true;
    }

    @Override
    public boolean verifyToken(String token) {
        return false;
    }
}
