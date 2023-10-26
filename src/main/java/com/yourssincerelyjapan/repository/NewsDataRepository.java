package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.NewsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsDataRepository extends JpaRepository<NewsData, Long> {
}
