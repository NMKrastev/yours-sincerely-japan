package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users_account_confirmation")
public class UserAccountConfirmation extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public UserAccountConfirmation(User user) {
        this.user = user;
        this.createdOn = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }
}
