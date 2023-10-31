package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    private String articleTitle;

    private String articleContent;

}
