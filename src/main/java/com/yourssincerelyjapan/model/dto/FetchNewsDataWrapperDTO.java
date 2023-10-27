package com.yourssincerelyjapan.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FetchNewsDataWrapperDTO {

    private final List<FetchNewsDataDTO> results;

    public FetchNewsDataWrapperDTO() {
        this.results = new ArrayList<>();
    }
}
