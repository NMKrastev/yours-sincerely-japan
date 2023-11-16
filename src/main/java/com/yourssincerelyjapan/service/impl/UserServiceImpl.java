package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.AdminConfiguration;
import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.dto.UserRegistrationDTO;
import com.yourssincerelyjapan.model.entity.UserProfilePicture;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.model.mapper.UserMapper;
import com.yourssincerelyjapan.event.OnRegistrationCompleteEvent;
import com.yourssincerelyjapan.repository.UserRepository;
import com.yourssincerelyjapan.repository.UserRoleRepository;
import com.yourssincerelyjapan.service.ProfilePictureService;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yourssincerelyjapan.constant.DTOValidationMessage.EMAIL_REGEX;
import static com.yourssincerelyjapan.constant.DTOValidationMessage.PASSWORD_REGEX;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProfilePictureService pictureService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserMapper userMapper;
    private final AdminConfiguration adminConfiguration;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           ProfilePictureService pictureService, UserMapper userMapper,
                           AdminConfiguration adminConfiguration, PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher eventPublisher) {

        this.userRepository = userRepository;
        this.pictureService = pictureService;
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

        UserProfilePicture profilePicture = null;

        if (!userDTO.getProfilePicture().isEmpty()) {
            profilePicture = this.pictureService.saveProfilePicture(userDTO.getProfilePicture());
        }

        final User newUser = this.userMapper.userRegistrationDtoToUserEntity(userDTO);
        newUser.setProfilePicture(profilePicture);
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
    public Optional<User> findUserByEmail(String username) {

        return this.userRepository
                .findByEmail(username);
    }

    @Override
    public UserDTO getUserDtoByEmail(UserDetails principal) {

        final User user = this.userRepository
                .findByEmail(principal.getUsername())
                .get();

        return this.userMapper.userToUserDto(user);
    }

    @Override
    public boolean updateFullName(UserDetails principal, String fullName) {

        if (fullName == null || fullName.isBlank() || fullName.length() > 30) {
            return false;
        }

        final User user = this.userRepository
                .findByEmail(principal.getUsername())
                .get();

        user.setFullName(fullName);

        final User saved = this.userRepository.save(user);

        return this.userRepository
                .findById(saved.getId())
                .isPresent();
    }

    @Override
    public boolean updateEmail(UserDetails principal, String email) {

        final Pattern pattern = Pattern.compile(EMAIL_REGEX);

        final Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return false;
        }

        if (this.userRepository.findByEmail(email).isPresent()) {
            return false;
        }

        this.invalidateUserAuthentication();

        final User user = this.userRepository
                .findByEmail(principal.getUsername())
                .get();

        user.setEmail(email);

        final User saved = this.userRepository.save(user);

        return this.userRepository
                .findById(saved.getId())
                .isPresent();
    }

    @Override
    public boolean updateProfilePicture(UserDetails principal, MultipartFile profilePicture) {

        if (profilePicture == null || profilePicture.isEmpty()) {
            return false;
        }

        final User user = this.userRepository
                .findByEmail(principal.getUsername())
                .get();

        final UserProfilePicture oldProfilePicture = user.getProfilePicture();

        final UserProfilePicture userProfilePicture =
                this.pictureService.saveProfilePicture(profilePicture);

        user.setProfilePicture(userProfilePicture);

        final User saved = this.userRepository.save(user);

        if (oldProfilePicture != null) {
            this.pictureService.deleteProfilePicture(oldProfilePicture);
        }

        return this.userRepository
                .findById(saved.getId())
                .isPresent();
    }

    @Override
    @Transactional
    public boolean deleteProfilePicture(UserDetails principal) {

        final User user = this.userRepository
                .findByEmail(principal.getUsername())
                .get();

        final UserProfilePicture profilePicture = user.getProfilePicture();

        user.setProfilePicture(null);

        final User saved = this.userRepository.save(user);

        this.pictureService.deleteProfilePicture(profilePicture);

        return this.userRepository
                .findById(saved.getId())
                .isPresent();
    }

    @Override
    public boolean updatePassword(UserDetails principal, String password) {

        if (password.length() < 8) {
            return false;
        }

        final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

        final Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            return false;
        }

        final User user = this.userRepository
                .findByEmail(principal.getUsername())
                .get();

        user.setPassword(this.passwordEncoder.encode(password));

        final User saved = this.userRepository.save(user);

        return this.userRepository
                .findById(saved.getId())
                .isPresent();
    }

    public void invalidateUserAuthentication() {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
