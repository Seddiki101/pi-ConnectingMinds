package com.maryem.forum.daos;

import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReponseRepository  extends JpaRepository<Reponse,Integer> {
    List<Reponse> findByQuestion(Question question);

}
