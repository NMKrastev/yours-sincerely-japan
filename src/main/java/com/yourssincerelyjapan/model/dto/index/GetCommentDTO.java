package com.yourssincerelyjapan.model.dto.index;

import com.yourssincerelyjapan.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentDTO {

    private String commentContent;

    private LocalDateTime createdOn;

    private GetUserDTO user;
}
