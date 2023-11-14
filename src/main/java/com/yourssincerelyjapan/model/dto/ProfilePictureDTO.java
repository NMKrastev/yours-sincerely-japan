package com.yourssincerelyjapan.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePictureDTO {

    private String name;

    private String type;

    private String imageDataBase64;
}
