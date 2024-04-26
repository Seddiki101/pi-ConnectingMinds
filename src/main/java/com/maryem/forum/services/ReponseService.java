package com.maryem.forum.services;

import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ReponseService {
    public Reponse ajouterReponse(String contenu , int idQuestion, MultipartFile imageFile);
    public Reponse updateAnswer(Reponse reponse );
    public void DeleteAnswer(Reponse reponse) ;
    public void DeleteAnswerById(int id);
    Reponse ReponseById(int id);
    public List<Reponse> getAllAnswers();
    public List<Reponse> getAllAnswersForQuestion(int idQuestion);
    public Reponse updateReponseById(Reponse reponse, int id) ;
    List<Reponse> searchComments(String contenu);

}
