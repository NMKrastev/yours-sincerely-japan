package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 1, max = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Size(min = 20)
    private String content;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "articles_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @Column(name = "created_on")
    @NotNull
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "articles__articles_pictures",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private List<ArticlePicture> pictures;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
