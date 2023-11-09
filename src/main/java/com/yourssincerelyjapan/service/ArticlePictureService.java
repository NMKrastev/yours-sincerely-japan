package com.yourssincerelyjapan.service;


import com.yourssincerelyjapan.model.entity.ArticlePicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticlePictureService {


    List<ArticlePicture> saveArticlePictures(List<MultipartFile> uploadImages);
}
