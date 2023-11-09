package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.Category;
import com.yourssincerelyjapan.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(CategoryEnum name);

}
