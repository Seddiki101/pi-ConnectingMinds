package com.maryem.forum.daos;

import com.maryem.forum.entities.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface QuestionRepository extends JpaRepository<Question, Integer> {


    @Query("SELECT r FROM Question r WHERE r.Contenu LIKE CONCAT('%', :contenu, '%')")
    List<Question> findByContenu(String contenu);

}
