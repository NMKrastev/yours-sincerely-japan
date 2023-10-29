package com.yourssincerelyjapan.utils;

import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.repository.UserRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleMapper {

    private UserRoleRepository userRoleRepository;

    public UserRoleMapper(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @UserRoleMapping
    public List<UserRole> mapUserRole() {

        return this.userRoleRepository
                .findByName(UserRoleEnum.USER);
    }
}
