package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "comment_content",nullable = false, columnDefinition = "LONGTEXT")
    @Size(min = 1)
    private String commentContent;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "users_comments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private User user;

    @ManyToOne
    @JoinTable(name = "articles_comments",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Article article;
}
