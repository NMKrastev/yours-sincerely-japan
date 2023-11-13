package com.yourssincerelyjapan.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.yourssincerelyjapan.constant.DTOValidationMessage.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {

    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    @Size(min = 1, max = 50, message = ARTICLE_TITLE_LENGTH)
    private String title;

    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    @Size(min = 20, message = ARTICLE_CONTENT_LENGTH)
    private String content;

    private List<MultipartFile> uploadImages;

    @NotEmpty(message = ARTICLE_CATEGORIES_SELECT)
    @NotNull
    private List<String> selected;

    private LocalDateTime createdOn;

    private String username;
}
