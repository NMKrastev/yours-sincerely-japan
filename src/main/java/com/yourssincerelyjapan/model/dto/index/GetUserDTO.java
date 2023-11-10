package com.yourssincerelyjapan.model.dto.index;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDTO {

    private String fullName;

    private GetUserProfilePictureDTO profilePicture;
}
