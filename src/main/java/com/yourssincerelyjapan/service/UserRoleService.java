package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.UserRoleDTO;

import java.util.List;
import java.util.Map;

public interface UserRoleService {

    List<UserRoleDTO> getAllRoles();

    Map<Long, UserRoleDTO> getAllRolesMap();

    Map<Long, String> rolesListToRolesMap(List<UserRoleDTO> roles);
}
