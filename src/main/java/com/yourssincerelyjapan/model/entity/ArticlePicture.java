package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article_pictures")
public class ArticlePicture extends BaseEntity {

    @Column
    private String name;

    @Column
    private String type;

    @Column(name = "image_data", columnDefinition = "LONGTEXT")
    private String imageData;

}
