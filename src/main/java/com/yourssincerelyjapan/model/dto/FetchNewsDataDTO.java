package com.yourssincerelyjapan.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FetchNewsDataDTO {

    private Long id;

    private String article_id;

    private String title;

    private String link;

    private String description;

    private String image_url;

    private String pubDate;
}
