package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.AdminConfiguration;
import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.model.mapper.UserMapper;
import com.yourssincerelyjapan.events.OnRegistrationCompleteEvent;
import com.yourssincerelyjapan.repository.UserRepository;
import com.yourssincerelyjapan.repository.UserRoleRepository;
import com.yourssincerelyjapan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserMapper userMapper;
    private final AdminConfiguration adminConfiguration;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           AdminConfiguration adminConfiguration, PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository, ApplicationEventPublisher eventPublisher) {

        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
        this.adminConfiguration = adminConfiguration;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void administratorInit() {

        if (this.userRepository.count() == 0) {

            final List<UserRole> roles = this.userRoleRepository.findAll();

            final User admin = User
                    .builder()
                    .fullName(this.adminConfiguration.getFullName())
                    .email(this.adminConfiguration.getEmail())
                    .password(this.passwordEncoder.encode(this.adminConfiguration.getPassword()))
                    .enabled(true)
                    .createdOn(LocalDateTime.now())
                    .roles(roles)
                    .build();

            this.userRepository.save(admin);
        }
    }

    @Override
    public boolean registerUser(UserRegistrationDTO userDTO) {

        if (this.userRepository.findByEmail(userDTO.getEmail()).isPresent()
                || !userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return false;
        }

        final User newUser = this.userMapper.userRegistrationDtoToUserEntity(userDTO);
        newUser.setRoles(this.userRoleRepository.findByName(UserRoleEnum.USER));
        newUser.setCreatedOn(LocalDateTime.now());

        final User savedUser = this.userRepository.save(newUser);

        this.eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedUser));

        return true;
    }

    @Override
    public void saveEnabledUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        return this.userRepository
                .findAll()
                .stream()
                .map(this.userMapper::userToUserDto)
                .toList();
    }

    @Override
    public UserDTO findUser(Long id) {

        return this.userRepository
                .findById(id)
                .map(this.userMapper::userToUserDto)
                .get();
    }

    @Override
    public boolean saveEditedUser(UserDTO userDTO, List<Long> selectedRoles) {

        User user = this.userRepository.findById(userDTO.getId()).get();

        if (!user.getFullName().equals(userDTO.getFullName())) {
            user.setFullName(userDTO.getFullName());
        }

        if (!userDTO.getEmail().equals(user.getEmail())) {
            if (this.userRepository.findByEmail(userDTO.getEmail()).isEmpty()) {
                user.setEmail(userDTO.getEmail());
            } else {
                return false;
            }
        }

        final List<UserRole> roles = new ArrayList<>();
        if (selectedRoles != null) {

            for (Long selectedRole : selectedRoles) {
                final UserRole userRole = this.userRoleRepository.findById(selectedRole).get();
                roles.add(userRole);
            }

            user.getRoles().clear();
            user.setRoles(roles);

        } else {
            //TODO: see how to tell that the user has to have selected roles
            return false;
        }

        if (user.isEnabled() != userDTO.isEnabled()) {
            user.setEnabled(userDTO.isEnabled());
        }

        user.setModifiedOn(LocalDateTime.now());

        //TODO: maybe do it with try/catch
        final User saved = this.userRepository.save(user);

        return this.userRepository.findById(saved.getId()).isPresent();
    }

    @Override
    public boolean deleteUser(Long id) {

        this.userRepository.deleteById(id);

        return this.userRepository
                .findById(id)
                .isEmpty();
    }
}
