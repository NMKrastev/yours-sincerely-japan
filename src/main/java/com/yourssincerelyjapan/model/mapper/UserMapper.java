package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.dto.index.GetUserDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.utils.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PasswordEncoderMapper.class, StringToLowerCaseMapper.class})
public interface UserMapper {

    @Mapping(target = "password", qualifiedBy = PasswordEncoderMapping.class)
    @Mapping(target = "email", qualifiedBy = StringToLowerCaseMapping.class)
    User userRegistrationDtoToUserEntity(UserRegistrationDTO userDTO);

    UserDTO userToUserDto(User user);

    GetUserDTO userToGetUserDto(User user);

    //User userDtoToUserEntity(UserDTO userDTO);
}
