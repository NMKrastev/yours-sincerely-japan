package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.utils.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "password", qualifiedBy = PasswordEncoderMapping.class)
    User userRegistrationDtoToUserEntity(UserRegistrationDTO userDTO);

    UserDTO userToUserDto(User user);

    //User userDtoToUserEntity(UserDTO userDTO);
}
