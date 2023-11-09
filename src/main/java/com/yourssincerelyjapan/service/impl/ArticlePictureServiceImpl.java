package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.ArticlePicture;
import com.yourssincerelyjapan.model.entity.UserProfilePicture;
import com.yourssincerelyjapan.repository.ArticlePictureRepository;
import com.yourssincerelyjapan.service.ArticlePictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ArticlePictureServiceImpl implements ArticlePictureService {

    private final ArticlePictureRepository articlePictureRepository;

    public ArticlePictureServiceImpl(ArticlePictureRepository articlePictureRepository) {
        this.articlePictureRepository = articlePictureRepository;
    }

    @Override
    public List<ArticlePicture> saveArticlePictures(List<MultipartFile> uploadImages) {

        final List<ArticlePicture> articlePictures = getArticlePictures(uploadImages);

        return this.articlePictureRepository.saveAll(articlePictures);

    }

    private List<ArticlePicture> getArticlePictures(List<MultipartFile> uploadImages) {

        final List<ArticlePicture> articlePictures = new ArrayList<>();

        for (MultipartFile image : uploadImages) {

            final String base64String;

            try {
                byte[] fileBytes = image.getBytes();
                base64String = Base64.getEncoder().encodeToString(fileBytes);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

            ArticlePicture articlePicture = ArticlePicture
                    .builder()
                    .name(image.getOriginalFilename())
                    .type(image.getContentType())
                    .imageData(base64String)
                    .build();

            articlePictures.add(articlePicture);
        }

        return articlePictures;
    }
}
