package com.maryem.forum.services;

import com.maryem.forum.entities.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionService {

   public Question ajouterQuestion(String contenu, MultipartFile imageFile) throws IOException;
   public Question updateQuestion(Question question );
   public void DeleteQuestion(Question question) ;
   public List<Question> getAllQuestion();
   //public Question ajouterQuestionI (String contenu , MultipartFile file);
}
