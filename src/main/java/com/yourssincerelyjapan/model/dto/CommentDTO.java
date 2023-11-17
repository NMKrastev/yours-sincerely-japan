package com.yourssincerelyjapan.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.yourssincerelyjapan.constant.DTOValidationMessage.COMMENT_LENGTH;
import static com.yourssincerelyjapan.constant.DTOValidationMessage.FIELD_MUST_NOT_BE_EMPTY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    @Size(min = 1, message = COMMENT_LENGTH)
    private String commentContent;

    private LocalDateTime createdOn;

    //private UserDTO user;

    //private ArticleDTO article;
}
