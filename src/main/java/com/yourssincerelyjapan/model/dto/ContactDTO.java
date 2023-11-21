package com.yourssincerelyjapan.model.dto;

import com.yourssincerelyjapan.validation.SiteEmailMatch;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.yourssincerelyjapan.constant.DTOValidationMessage.*;
import static com.yourssincerelyjapan.constant.DTOValidationMessage.VALID_EMAIL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {

    @NotEmpty(message = MANDATORY_FIELD)
    @Size(min = 1, max = 30, message = FULL_NAME_LENGTH)
    private String fullName;

    @NotEmpty(message = MANDATORY_FIELD)
    @Pattern(regexp = EMAIL_REGEX, message = VALID_EMAIL)
    @SiteEmailMatch
    private String email;

    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    @Size(min = 1, message = CONTACT_CONTENT_LENGTH)
    private String contactContent;
}
