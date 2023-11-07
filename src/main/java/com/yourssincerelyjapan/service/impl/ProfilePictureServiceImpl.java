package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.ProfilePicture;
import com.yourssincerelyjapan.repository.ProfilePictureRepository;
import com.yourssincerelyjapan.service.ProfilePictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ProfilePictureServiceImpl implements ProfilePictureService {

    private final ProfilePictureRepository pictureRepository;

    public ProfilePictureServiceImpl(ProfilePictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public ProfilePicture saveProfilePicture(MultipartFile image) {

        final ProfilePicture profilePicture = getProfilePicture(image);

        return this.pictureRepository.save(profilePicture);
    }

    private ProfilePicture getProfilePicture(MultipartFile image) {

        final String base64String;

        try {
            byte[] fileBytes = image.getBytes();
            base64String = Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final ProfilePicture profilePicture = ProfilePicture
                .builder()
                .name(image.getOriginalFilename())
                .type(image.getContentType())
                .imageDataBase64(base64String)
                .build();

        return profilePicture;
    }
}
