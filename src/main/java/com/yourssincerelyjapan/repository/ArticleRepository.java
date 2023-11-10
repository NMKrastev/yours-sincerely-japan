package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticleByUser(User user);
}
