package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.dto.UserDTO;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.repository.*;
import com.yourssincerelyjapan.service.AdminService;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProfilePictureRepository profilePictureRepository;
    private final ArticlePictureRepository articlePictureRepository;
    private final ArticleRepository articleRepository;
    private final ConfirmationRepository confirmationRepository;
    private final SessionRegistry sessionRegistry;

    public AdminServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                            ProfilePictureRepository profilePictureRepository, ArticlePictureRepository articlePictureRepository,
                            ArticleRepository articleRepository, ConfirmationRepository confirmationRepository,
                            SessionRegistry sessionRegistry) {

        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.profilePictureRepository = profilePictureRepository;
        this.articlePictureRepository = articlePictureRepository;
        this.articleRepository = articleRepository;
        this.confirmationRepository = confirmationRepository;
        this.sessionRegistry = sessionRegistry;
    }


    @Override
    public boolean saveEditedUser(UserDTO userDTO, List<Long> selectedRoles) {

        User user = this.userRepository.findById(userDTO.getId()).get();

        if (!user.getFullName().equals(userDTO.getFullName())) {
            user.setFullName(userDTO.getFullName());
        }

        if (!userDTO.getEmail().equals(user.getEmail())) {
            if (this.userRepository.findByEmail(userDTO.getEmail()).isEmpty()) {
                this.logoutUser(user.getEmail());
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

    private void logoutUser(String username) {

        final List<Object> principals = this.sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            if (principal instanceof UserDetails userDetails) {
                if (userDetails.getUsername().equals(username)) {
                    // Invalidate each session associated with the user
                    final List<SessionInformation> sessions =
                            this.sessionRegistry.getAllSessions(userDetails, false);
                    for (SessionInformation sessionInformation : sessions) {
                        sessionInformation.expireNow();
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {

        final User user = this.userRepository.findById(id).get();

        final Optional<UserAccountConfirmation> confirmation = this.confirmationRepository.findByUser(user);

        confirmation.ifPresent(this.confirmationRepository::delete);

        this.profilePictureRepository.delete(user.getProfilePicture());

        user.getArticles()
                .forEach(a -> a.getPictures().forEach(p -> this.articlePictureRepository.deleteById(p.getId())));

        user.getArticles()
                .forEach(a -> this.articleRepository.deleteById(a.getId()));

        //final User saved = this.userRepository.save(user);

        this.userRepository.deleteById(user.getId());

        return this.userRepository
                .findById(id)
                .isEmpty();
    }
}
