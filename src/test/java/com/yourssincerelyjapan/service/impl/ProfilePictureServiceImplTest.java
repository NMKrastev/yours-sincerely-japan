package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.UserProfilePicture;
import com.yourssincerelyjapan.repository.ProfilePictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfilePictureServiceImplTest {

    @Mock
    private ProfilePictureRepository pictureRepository;

    @Mock
    private ProfilePictureServiceImpl pictureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pictureService = new ProfilePictureServiceImpl(pictureRepository);
    }

    @Test
    void saveProfilePicture() {

        MockMultipartFile multipartFile = getImage();

        UserProfilePicture mockProfilePicture = getProfilePicture();

        when(pictureRepository.save(any())).thenReturn(mockProfilePicture);

        UserProfilePicture savedProfilePicture = pictureService.saveProfilePicture(multipartFile);

        assertNotNull(savedProfilePicture);
        assertEquals("test.jpg", savedProfilePicture.getName());
        assertEquals("image/jpeg", savedProfilePicture.getType());
        assertNotNull(savedProfilePicture.getImageDataBase64());
    }

    @Test
    void deleteProfilePicture() {

        UserProfilePicture mockProfilePicture = getProfilePicture();

        pictureService.deleteProfilePicture(mockProfilePicture);

        verify(pictureRepository).delete(mockProfilePicture);
    }

    private static MockMultipartFile getImage() {
        return new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "Mock image content".getBytes()
        );
    }

    private static UserProfilePicture getProfilePicture() {
        return UserProfilePicture.builder()
                .name("test.jpg")
                .type("image/jpeg")
                .imageDataBase64(Base64.getEncoder().encodeToString("Mock image content".getBytes()))
                .build();
    }
}