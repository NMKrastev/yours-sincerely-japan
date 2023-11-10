package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findArticleByUserOrderByCreatedOnDesc(Pageable pageable, User user);
}
