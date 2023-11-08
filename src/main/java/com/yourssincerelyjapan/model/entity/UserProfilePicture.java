package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profile_pictures")
public class UserProfilePicture extends BaseEntity {

    @Column
    private String name;

    @Column
    private String type;

    @Column(name = "image_data", columnDefinition = "LONGTEXT")
    private String imageDataBase64;

    @OneToOne(mappedBy = "profilePicture")
    private User user;
}
