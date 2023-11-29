package com.yourssincerelyjapan.utils;

import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserRoleMapperTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRoleMapper userRoleMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRoleMapper = new UserRoleMapper(userRoleRepository);
    }

    @Test
    void mapUserRole() {
        when(userRoleRepository.findByName(UserRoleEnum.USER))
                .thenReturn(Collections.singletonList(new UserRole()));

        List<UserRole> userRoles = userRoleMapper.mapUserRole();

        assertEquals(1, userRoles.size());
    }
}