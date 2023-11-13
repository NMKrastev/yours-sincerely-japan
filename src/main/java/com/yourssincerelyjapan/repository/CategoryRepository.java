package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(CategoryEnum name);

    //List<Category> findAllByAndLatestCreatedArticleNotNullOrderByLatestCreatedArticleDesc();

    @Query("""
            SELECT c FROM Category AS c
            WHERE c.latestCreatedArticle IS NOT NULL
            ORDER BY c.latestCreatedArticle DESC
            LIMIT 5
            """)
    List<Category> findFiveLatestCategories();

    Page<Category> findAllByOrderByName(Pageable pageable);
}
