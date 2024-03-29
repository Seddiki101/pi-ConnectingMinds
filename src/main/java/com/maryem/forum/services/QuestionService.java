package com.maryem.forum.services;

import com.maryem.forum.entities.Question;

import java.util.List;

public interface QuestionService {
   public Question ajouterQuestion (Question question);
   public Question updateQuestion(Question question );
   public void DeleteQuestion(Question question) ;
   public List<Question> getAllQuestion();
}
