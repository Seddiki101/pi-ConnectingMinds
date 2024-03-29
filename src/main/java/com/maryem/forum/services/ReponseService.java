package com.maryem.forum.services;

import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;

import java.util.List;


public interface ReponseService {
    public Reponse ajouterReponse(Reponse reponse , int idQuestion);
    public Reponse updateAnswer(Reponse reponse );
    public void DeleteAnswer(Reponse reponse) ;
    public List<Reponse> getAllAnswers();
    public List<Reponse> getAllAnswersForQuestion(int idQuestion);
}
