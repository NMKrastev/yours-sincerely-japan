package com.yourssincerelyjapan.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

import static com.yourssincerelyjapan.constant.DTOValidationMessage.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty(message = MANDATORY_FIELD)
    @Size(min = 1, max = 30, message = FULL_NAME_LENGTH)
    private String fullName;

    @NotEmpty(message = MANDATORY_FIELD)
    @Pattern(regexp = EMAIL_REGEX, message = VALID_EMAIL)
    private String email;

    /*@NotEmpty(message = "Choose a role!")
    private List<UserRoleDTO> roles;*/

    @NotNull
    private boolean enabled;
}
