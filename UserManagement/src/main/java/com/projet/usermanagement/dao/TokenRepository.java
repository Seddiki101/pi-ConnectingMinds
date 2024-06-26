package com.projet.usermanagement.dao;

import com.projet.usermanagement.security.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query("""
select t from Token t inner join User u on t.user.userId = u.userId
where t.user.userId = :userId and t.loggedOut = false
""")
    List<Token> findAllTokensByUser(Long userId);

    Optional<Token> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.loggedOut = true")
    void deleteAllLoggedOutTokens();

}
