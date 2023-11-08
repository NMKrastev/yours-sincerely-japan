package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.UserProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePictureRepository extends JpaRepository<UserProfilePicture, Long> {

}
