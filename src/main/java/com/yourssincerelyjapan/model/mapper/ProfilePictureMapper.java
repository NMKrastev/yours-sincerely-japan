package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.ProfilePictureDTO;
import com.yourssincerelyjapan.model.entity.UserProfilePicture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfilePictureMapper {

    ProfilePictureDTO profilePictureToProfilePictureDto(UserProfilePicture profilePicture);
}