package com.yourssincerelyjapan.config;

import com.yourssincerelyjapan.model.enums.UserRoleEnum;
import com.yourssincerelyjapan.repository.UserRepository;
import com.yourssincerelyjapan.user.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    session.sessionAuthenticationStrategy(new RegisterSessionAuthenticationStrategy(sessionRegistry()));
                    session.sessionConcurrency(s -> s.maximumSessions(1));
                })
                .authorizeHttpRequests(
                        // Define which urls are visible by which users
                        authorizeRequests -> authorizeRequests
                                // All static resources which are situated in js, images, css are available for anyone
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/contacts", "/about", "/privacy-policy").permitAll()
                                // Allow anyone to see the home page, the registration page and the login form
                                .requestMatchers("/", "/contacts", "/users/registration", "/users/login", "/users/login-error", "/users/account-verification").permitAll()
                                .requestMatchers("/japan/api/news", "/japan/news", "/japan/history").permitAll()
                                .requestMatchers("/categories/all", "/categories/{category}").permitAll()
                                .requestMatchers("/articles/single-article").permitAll()
                                .requestMatchers("/articles/single-article/{id}").permitAll()
                                .requestMatchers("/articles/new").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers("/articles/single-article/comment/{articleID}").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers("/articles/single-article/comment/edit/{id}").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers("/articles/single-article/comment/delete/{id}").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers(HttpMethod.PATCH, "/articles/edit/{id}").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers(HttpMethod.DELETE, "/articles/delete/{id}").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers("/users/profile", "/users/profile").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers(HttpMethod.PATCH, "/users/profile/fullName").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers(HttpMethod.PATCH, "/users/profile/email").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers(HttpMethod.DELETE, "/users/profile/deleteProfilePicture").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers(HttpMethod.PATCH, "/users/profile/profilePicture").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers(HttpMethod.PATCH, "/users/profile/password").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers("/users/articles").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers("/pictures/delete/{id}").hasRole(UserRoleEnum.USER.name())
                                .requestMatchers("/admin/users/all").hasRole(UserRoleEnum.ADMIN.name())
                                .requestMatchers(HttpMethod.PATCH, "/admin/users/edit/{id}").hasRole(UserRoleEnum.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/admin/users/delete/{id}").hasRole(UserRoleEnum.ADMIN.name())
                                // all other requests are authenticated.
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        formLogin -> {
                            formLogin
                                    // redirect here when we access something which is not allowed.
                                    // also this is the page where we perform login.
                                    .loginPage("/users/login")
                                    // The names of the input fields (in our case in auth-login.html)
                                    .usernameParameter("email")
                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/", true)
                                    .failureForwardUrl("/users/login-error");
                        }
                )
                .logout(
                        logout -> {
                            logout
                                    // the URL where we should POST something in order to perform the logout
                                    .logoutUrl("/users/logout")
                                    // where to go when logged out?
                                    .logoutSuccessUrl("/")
                                    // invalidate the HTTP session
                                    .invalidateHttpSession(true)
                                    .deleteCookies("JSESSIONID");
                        }
                )
                /*.csrf(AbstractHttpConfigurer::disable)*/
                .build();

        /*.rememberMe(
                rememberMe -> {
                    rememberMe
                            .key(rememberMeKey)
                            .rememberMeParameter("rememberme")
                            .rememberMeCookieName("rememberme");
                }
        )*/
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        // This service translates the application users and roles
        // to representation which spring security understands.
        return new AppUserDetailsService(userRepository);
    }

    @Bean
    public SessionRegistry sessionRegistry() {

        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {

        return new HttpSessionEventPublisher();
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {

        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
