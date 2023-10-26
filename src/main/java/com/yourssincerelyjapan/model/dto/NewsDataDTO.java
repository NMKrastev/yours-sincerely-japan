package com.yourssincerelyjapan.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewsDataDTO {

    private String title;

    private String link;

    private String description;

    private String image_url;

    private String pubDate;
}
