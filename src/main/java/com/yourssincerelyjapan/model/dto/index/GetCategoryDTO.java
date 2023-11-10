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
public class GetCategoryDTO {

    private String name;

    private LocalDateTime latestCreatedArticle;

    private List<GetArticleDTO> articles;
}
