package com.maryem.forum.daos;

import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReponseRepository  extends JpaRepository<Reponse,Integer> {
    List<Reponse> findByQuestion(Question question);
    @Query("SELECT r FROM Reponse r WHERE r.Contenu LIKE CONCAT('%', :contenu, '%')")
    List<Reponse> findByContenu(String contenu);

}
