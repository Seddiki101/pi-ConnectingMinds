package com.maryem.forum.daos;

import com.maryem.forum.entities.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Question q "  // Use 'q' as the entity alias
            + "SET q.Contenu = ?1, "  // Use comma (,) to separate updates
            + "q.image = ?2 "
            + "WHERE q.idQuestion = ?3")
    int updateQuestion(String contenu, String image, int id); // Use a descriptive method name
}
