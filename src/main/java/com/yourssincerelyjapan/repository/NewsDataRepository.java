package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.NewsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsDataRepository extends JpaRepository<NewsData, Long> {

    List<NewsData> findAllByOrderByIdDesc();

    Optional<NewsData> findNewsByFetchArticleId(String articleId);
}
