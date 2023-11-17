package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.CommentDTO;
import com.yourssincerelyjapan.model.dto.index.GetArticleDTO;
import com.yourssincerelyjapan.model.dto.index.GetCommentDTO;
import com.yourssincerelyjapan.model.entity.Article;
import com.yourssincerelyjapan.model.entity.Comment;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.mapper.CommentMapper;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.repository.CommentRepository;
import com.yourssincerelyjapan.repository.UserRepository;
import com.yourssincerelyjapan.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
                              ArticleRepository articleRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public boolean createComment(CommentDTO commentDTO, Long articleId, UserDetails principal) {

        final Comment comment = this.commentMapper.commentDtoToComment(commentDTO);

        final User user = this.userRepository
                .findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with email %s not found!", principal.getUsername())));

        final Article article = this.articleRepository
                .findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Article with id %d not found!", articleId)));

        comment.setUser(user);
        comment.setArticle(article);

        this.commentRepository.save(comment);

        return true;
    }

    @Override
    public Page<GetCommentDTO> getArticleComments(Pageable pageable, Long id) {

        final Page<Comment> comments = this.commentRepository
                .findByArticle_IdOrderByCreatedOnDesc(pageable, id);

        final List<GetCommentDTO> articleComments = comments
                .getContent()
                .stream()
                .map(this.commentMapper::commentToGetCommentDto)
                .toList();

        return new PageImpl<>(articleComments, pageable, comments.getTotalElements());
    }
}
