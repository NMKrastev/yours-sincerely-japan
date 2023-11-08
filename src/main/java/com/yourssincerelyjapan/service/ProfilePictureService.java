package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.entity.UserProfilePicture;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {

    UserProfilePicture saveProfilePicture(MultipartFile image);
}
