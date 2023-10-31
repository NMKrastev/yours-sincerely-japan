package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    private List<String> selected;
}
