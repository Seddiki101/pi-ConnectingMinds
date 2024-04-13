package com.projet.usermanagement.dao;

import com.projet.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional <User> findUserByEmail(String email) ;

    @Query("SELECT u FROM User u JOIN u.tokens t WHERE t.token = :token")
    Optional<User> findUserByToken(@Param("token") String token);

    Optional<User> findByResetPasswordToken(String token);
   // Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enable = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);


}
