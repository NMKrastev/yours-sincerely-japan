package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByArticle_IdOrderByCreatedOnDesc(Pageable pageable, Long id);
}
