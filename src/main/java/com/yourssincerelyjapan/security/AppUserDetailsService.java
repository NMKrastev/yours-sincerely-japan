package com.yourssincerelyjapan.security;

import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserRole;
import com.yourssincerelyjapan.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        Optional<User> optUser = this.userRepository.findByEmailIgnoreCase(email);

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("Email: " + email + " is not found!");
        }

        final User user = optUser.get();


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final List<UserRole> roles) {
        return getGrantedAuthorities(roles);
    }


    //TODO: Might need to accept List instead of Collection
    private List<GrantedAuthority> getGrantedAuthorities(final List<UserRole> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final UserRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(String.valueOf(role.getName())));
        }
        return authorities;
    }
}
