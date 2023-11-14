package com.yourssincerelyjapan.repository;

import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<UserAccountConfirmation, Long> {

    UserAccountConfirmation findByToken(String token);
}
