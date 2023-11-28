package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.ArticlePicture;
import com.yourssincerelyjapan.repository.ArticlePictureRepository;
import com.yourssincerelyjapan.service.ArticlePictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ArticlePictureServiceImplTest {

    @Mock
    private ArticlePictureRepository articlePictureRepository;

    @Mock
    private ArticlePictureService articlePictureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articlePictureService = new ArticlePictureServiceImpl(articlePictureRepository);
    }

    @Test
    void saveArticlePictures() throws IOException {

        MultipartFile mockImage = mock(MultipartFile.class);
        when(mockImage.getBytes()).thenReturn(new byte[]{1, 2, 3}); // Mocking image bytes
        when(mockImage.getOriginalFilename()).thenReturn("test.jpg");
        when(mockImage.getContentType()).thenReturn("image/jpeg");

        List<MultipartFile> uploadImages = List.of(mockImage);

        List<ArticlePicture> articlePictures = getArticlePictures();
        when(articlePictureRepository.saveAll(anyList())).thenReturn(articlePictures);

        List<ArticlePicture> result = articlePictureService.saveArticlePictures(uploadImages);

        assertEquals(1, result.size());
        assertEquals(articlePictures.get(0).getName(), result.get(0).getName());
        verify(articlePictureRepository, times(1)).saveAll(anyList());

    }

    @Test
    void deleteArticlePictures() {

        Article article = getArticle();

        articlePictureService.deleteArticlePictures(article);

        verify(articlePictureRepository, times(1)).deleteAll(article.getPictures());

    }

    private List<ArticlePicture> getArticlePictures() {

        return List.of(ArticlePicture
                .builder()
                .name("test")
                .type("image/jpeg")
                .imageData("dawdawdawd")
                .build());
    }

    private Article getArticle() {

        return Article
                .builder()
                .title("test")
                .content("test 123 123")
                .pictures(getArticlePictures())
                .build();
    }
}