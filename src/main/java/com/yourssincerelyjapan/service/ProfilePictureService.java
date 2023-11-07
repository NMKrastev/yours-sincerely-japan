package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.entity.ProfilePicture;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {

    ProfilePicture saveProfilePicture(MultipartFile image);
}
