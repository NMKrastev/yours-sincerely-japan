package com.yourssincerelyjapan.model.dto;

import com.yourssincerelyjapan.validation.FieldMatch;
import com.yourssincerelyjapan.validation.UniqueUserEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.yourssincerelyjapan.constant.DTOValidationMessage.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword", message = PASSWORD_DOES_NOT_MATCH)
public class UserRegistrationDTO {

    @NotEmpty(message = MANDATORY_FIELD)
    @Size(min = 1, max = 30, message = FULL_NAME_LENGTH)
    private String fullName;

    @NotEmpty(message = MANDATORY_FIELD)
    @Pattern(regexp = EMAIL_REGEX, message = VALID_EMAIL)
    @UniqueUserEmail(message = EMAIL_ALREADY_IN_USE)
    private String email;

    @NotEmpty(message = MANDATORY_FIELD)
    @Size(min = 8, max = 20, message = PASSWORD_LENGTH)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_SPECIAL_SYMBOL)
    private String password;

    @NotEmpty(message = MANDATORY_FIELD)
    @Size(min = 8, max = 20, message = PASSWORD_LENGTH)
    private String confirmPassword;

    private LocalDateTime createdOn;

    private List<UserRoleDTO> roles;

    private MultipartFile profilePicture;
}
