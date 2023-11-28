package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.model.mapper.UserRoleMapper;
import com.yourssincerelyjapan.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Mock
    private UserRoleServiceImpl userRoleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRoleService = new UserRoleServiceImpl(userRoleRepository, userRoleMapper);
    }

    @Test
    void getAllRoles() {

        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(new UserRole(UserRoleEnum.USER));
        mockRoles.add(new UserRole(UserRoleEnum.ADMIN));

        List<UserRoleDTO> mockRoleDTOs = new ArrayList<>();
        mockRoleDTOs.add(new UserRoleDTO(1L, "ROLE_USER"));
        mockRoleDTOs.add(new UserRoleDTO(2L, "ROLE_ADMIN"));

        when(userRoleRepository.findAll()).thenReturn(mockRoles);
        when(userRoleMapper.userRoleToUserRoleDTO(mockRoles.get(0))).thenReturn(mockRoleDTOs.get(0));
        when(userRoleMapper.userRoleToUserRoleDTO(mockRoles.get(1))).thenReturn(mockRoleDTOs.get(1));

        List<UserRoleDTO> result = userRoleService.getAllRoles();

        assertEquals(mockRoleDTOs, result);
    }

    @Test
    void getAllRolesMap() {

        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(new UserRole(UserRoleEnum.USER));

        List<UserRoleDTO> mockRoleDTOs = new ArrayList<>();
        mockRoleDTOs.add(new UserRoleDTO(1L, "ROLE_USER"));

        when(userRoleRepository.findAll()).thenReturn(mockRoles);

        when(userRoleMapper.userRoleToUserRoleDTO(mockRoles.get(0))).thenReturn(mockRoleDTOs.get(0));

        Map<Long, UserRoleDTO> roleDTOMap = userRoleService.getAllRolesMap();

        assertEquals(mockRoleDTOs.size(), roleDTOMap.size());
        assertEquals(mockRoleDTOs.get(0).getName(), roleDTOMap.get(null).getName());
    }

    @Test
    void rolesListToRolesMap() {

        List<UserRoleDTO> mockRoleDTOs = new ArrayList<>();
        mockRoleDTOs.add(new UserRoleDTO(1L, "ROLE_USER"));
        mockRoleDTOs.add(new UserRoleDTO(2L, "ROLE_ADMIN"));

        Map<Long, String> roles = userRoleService.rolesListToRolesMap(mockRoleDTOs);

        assertEquals(mockRoleDTOs.size(), roles.size());
        assertEquals(mockRoleDTOs.get(0).getName(), userRoleService.rolesListToRolesMap(mockRoleDTOs).get(1L));
        assertEquals(mockRoleDTOs.get(1).getName(), userRoleService.rolesListToRolesMap(mockRoleDTOs).get(2L));
    }
}