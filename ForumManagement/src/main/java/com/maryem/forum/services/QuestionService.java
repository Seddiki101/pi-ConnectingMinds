package com.maryem.forum.services;

import com.maryem.forum.entities.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionService {

   public Question ajouterQuestion(String contenu,String firstName,String lastName, MultipartFile imageFile , Long userId) throws IOException;

   public void DeleteQuestion(int id);
   public List<Question> getAllQuestion();
   Question QuestionById(int id);
   //public Question ajouterQuestionI (String contenu , MultipartFile file);
   public Question updateQuestionById(Question question, int id) ;
   List<Question> searchPosts(String contenu);
   public void addLikeToQuestion(int questionId);
   public void removeLikeFromQuestion(int questionId);


   }
