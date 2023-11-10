package com.yourssincerelyjapan.model.dto.index;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleDTO {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdOn;

    private List<GetArticlePictureDTO> pictures;

    private GetUserDTO user;
}
