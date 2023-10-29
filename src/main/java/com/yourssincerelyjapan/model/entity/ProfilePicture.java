package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profile_pictures")
public class ProfilePicture extends BaseEntity {
}
