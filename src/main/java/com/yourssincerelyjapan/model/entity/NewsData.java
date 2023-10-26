package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "news_data")
public class NewsData extends BaseEntity {

    @Column
    private String title;

    @Column(name = "news_url")
    private String newsUrl;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
