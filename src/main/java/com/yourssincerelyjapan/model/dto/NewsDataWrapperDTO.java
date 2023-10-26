package com.yourssincerelyjapan.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class NewsDataWrapperDTO {

    private final List<NewsDataDTO> results;

    public NewsDataWrapperDTO() {
        this.results = new ArrayList<>();
    }
}
