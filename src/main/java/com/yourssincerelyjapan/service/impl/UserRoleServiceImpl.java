package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.mapper.UserRoleMapper;
import com.yourssincerelyjapan.repository.UserRoleRepository;
import com.yourssincerelyjapan.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<UserRoleDTO> getAllRoles() {

        return this.userRoleRepository
                .findAll()
                .stream()
                .map(this.userRoleMapper::userRoleToUserRoleDTO)
                .toList();
    }

    @Override
    public Map<Long, UserRoleDTO> getAllRolesMap() {

        final List<UserRole> allRoles = this.userRoleRepository
                .findAll();

        Map<Long, UserRoleDTO> allRolesMap = new HashMap<>();

        for (UserRole role : allRoles) {

            allRolesMap.put(role.getId(), this.userRoleMapper.userRoleToUserRoleDTO(role));

        }

        return allRolesMap;
    }

    @Override
    public Map<Long, String> rolesListToRolesMap(List<UserRoleDTO> roles) {

        final Map<Long, String> userRoles = new HashMap<>();

        for (UserRoleDTO role : roles) {
            userRoles.put(role.getId(), role.getName());
        }
        return userRoles;
    }
}
