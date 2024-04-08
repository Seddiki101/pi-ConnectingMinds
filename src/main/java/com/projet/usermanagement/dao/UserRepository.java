package com.projet.usermanagement.dao;

import com.projet.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional <User> findUserByEmail(String email) ;
   // Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enable = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);


}
