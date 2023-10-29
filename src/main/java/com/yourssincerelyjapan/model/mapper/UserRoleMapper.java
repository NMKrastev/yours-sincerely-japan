package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.UserRoleDTO;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.utils.UserRoleMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserRoleMapper.class)
public interface UserRoleMapper {

    @Mapping(target = "roles", qualifiedBy = UserRoleMapping.class)
    List<UserRole> userRoleDtoToUserRole(List<UserRoleDTO> userRoles);
}
