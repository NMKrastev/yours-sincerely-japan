package com.yourssincerelyjapan.model.dto;

import com.yourssincerelyjapan.model.entity.ProfilePicture;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.validation.FieldMatch;
import com.yourssincerelyjapan.validation.UniqueUserEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword", message = "Password does not match!")
public class UserRegistrationDTO {

    @NotEmpty
    @Size(min = 1, max = 30)
    private String fullName;

    @NotEmpty(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    @UniqueUserEmail(message = "Email is already in use!")
    private String email;

    @NotEmpty
    @Size(min = 8, max = 20, message = "TODO: Password length message!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#%&$*])", message = "TODO: Password special symbols message!")
    private String password;

    @NotEmpty
    @Size(min = 8)
    private String confirmPassword;

    private LocalDateTime createdOn;

    private List<UserRoleDTO> roles;

    //private ProfilePicture profilePicture;
}
