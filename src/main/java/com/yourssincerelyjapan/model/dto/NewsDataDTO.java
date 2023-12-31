package com.yourssincerelyjapan.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewsDataDTO {

    private Long id;

    private String title;

    private String newsUrl;

    private String description;

    private String imageUrl;

    private String createdOn;
}
