package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.CommentDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.yourssincerelyjapan.constant.AppConstants.*;

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
                .orElseThrow(() -> new IllegalArgumentException(String.format(USER_WITH_EMAIL_NOT_FOUND, principal.getUsername())));

        final Article article = this.articleRepository
                .findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(ARTICLE_WITH_ID_NOT_FOUND, articleId)));

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

    @Override
    public void deleteCommentsFromDeletedArticle(List<Comment> comments) {

        comments.forEach(c -> c.setArticle(null));
        comments.forEach(c -> c.setUser(null));

        this.commentRepository.saveAll(comments);

        this.commentRepository.deleteAll(comments);
    }

    @Override
    public Long editComment(Long id, String commentContent) {

        final Comment comment = this.commentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format(USER_WITH_ID_NOT_FOUND, id)));

        final Long articleId = comment.getArticle().getId();

        if (commentContent.isEmpty()) {
            return articleId;
        }

        comment.setCommentContent(commentContent);
        comment.setModifiedOn(LocalDateTime.now());

        this.commentRepository.save(comment);

        return articleId;
    }

    @Override
    @Transactional
    public Long deleteComment(Long id) throws IllegalArgumentException {

        final Comment comment = this.commentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format(COMMENT_WITH_ID_NOT_FOUND, id)));

        final Long articleId = comment.getArticle().getId();

        comment.setUser(null);
        comment.setArticle(null);

        this.commentRepository.delete(comment);

        return articleId;
    }
}
