package com.yourssincerelyjapan.model.entity;

import com.yourssincerelyjapan.model.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryEnum name;

    @Column(name = "latest_created_article")
    private LocalDateTime latestCreatedArticle;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    private List<Article> articles;

}
