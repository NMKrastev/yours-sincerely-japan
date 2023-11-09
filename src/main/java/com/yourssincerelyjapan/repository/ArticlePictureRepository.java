package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.ArticlePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlePictureRepository extends JpaRepository<ArticlePicture, Long> {

}
