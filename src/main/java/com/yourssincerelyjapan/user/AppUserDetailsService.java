package com.yourssincerelyjapan.user;

import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static com.yourssincerelyjapan.constant.AppConstants.ROLE_;
import static com.yourssincerelyjapan.constant.AppConstants.USER_WITH_EMAIL_NOT_FOUND;

@Transactional
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        return this.userRepository
                .findByEmailIgnoreCase(email)
                .map(AppUserDetailsService::getUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, email)));
    }

    private static UserDetails getUserDetails(User user) {

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                user.getRoles().stream().map(AppUserDetailsService::getGranterAuthorities).toList()
        );
    }

    private static GrantedAuthority getGranterAuthorities(UserRole userRole) {
        return new SimpleGrantedAuthority(ROLE_ + userRole.getName().name());
    }
}
