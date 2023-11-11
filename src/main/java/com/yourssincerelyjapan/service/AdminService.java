package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserDTO;

import java.util.List;

public interface AdminService {

    boolean saveEditedUser(UserDTO userDTO, List<Long> selectedRoles);

    boolean deleteUser(Long id);
}
