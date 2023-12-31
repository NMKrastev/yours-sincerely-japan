package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.CommentDTO;
import com.yourssincerelyjapan.model.dto.index.GetCommentDTO;
import com.yourssincerelyjapan.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {

    boolean createComment(CommentDTO commentDTO, Long articleId, UserDetails principal);

    Page<GetCommentDTO> getArticleComments(Pageable pageable, Long id);

    void deleteCommentsFromDeletedArticle(List<Comment> comments);

    Long editComment(Long id, String commentContent);

    Long deleteComment(Long id);
}
