package com.yourssincerelyjapan.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yourssincerelyjapan.constant.DTOValidationMessage.ARTICLE_CONTENT_LENGTH;
import static com.yourssincerelyjapan.constant.DTOValidationMessage.ARTICLE_TITLE_LENGTH;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ArticleDTO {

    @NotEmpty
    @Size(min = 1, max = 50, message = ARTICLE_TITLE_LENGTH)
    private String title;

    @NotEmpty
    @Size(min = 20, message = ARTICLE_CONTENT_LENGTH)
    private String content;

    private List<MultipartFile> uploadImages;

    private List<String> selected;

    private LocalDateTime createdOn;

    public ArticleDTO() {
        this.createdOn = LocalDateTime.now();
    }
}
