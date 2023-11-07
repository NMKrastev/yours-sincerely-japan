package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.ProfilePictureDTO;
import com.yourssincerelyjapan.model.entity.ProfilePicture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfilePictureMapper {

    ProfilePictureDTO profilePictureToProfilePictureDto(ProfilePicture profilePicture);
}