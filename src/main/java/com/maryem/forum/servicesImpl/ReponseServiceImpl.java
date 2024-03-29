package com.maryem.forum.servicesImpl;

import com.maryem.forum.daos.QuestionRepository;
import com.maryem.forum.daos.ReponseRepository;
import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import com.maryem.forum.services.ReponseService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ReponseServiceImpl implements ReponseService {
    @Resource
    ReponseRepository reponseRepository;
    @Resource
    QuestionRepository questionRepository;
    private static final Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find an answer with id : %s";

    private static final String ERROR_UPDATE = "Error occured while updating";
    @Override
    @Transactional
    public Reponse ajouterReponse(Reponse reponse , int idQuestion) {
        // Vérifier si la réponse est null
        if (reponse == null) {
            LOG.error("Posted answer is NULL");
            return null;
        }

        // Récupérer la question correspondante
        Question question = questionRepository.findById(idQuestion).orElse(null);

        // Vérifier si la question existe
        if (question == null) {
            LOG.error(String.format(ERROR_NON_PRESENT_ID, idQuestion));
            return null;
        }

        // Créer une nouvelle instance de Reponse pour éviter de modifier l'objet d'origine
        Reponse newReponse = new Reponse();

        // Copier le contenu fourni
        newReponse.setContenu(reponse.getContenu());

        // Associer la réponse à la question
        newReponse.setQuestion(question);

        // Mettre à jour la date de création si elle est nulle
        if (reponse.getCreatedAt() == null) {
            newReponse.setCreatedAt(LocalDateTime.now());
        }

        // Enregistrer la réponse dans la base de données
        return reponseRepository.save(newReponse);
    }

    @Override
    public Reponse updateAnswer(Reponse reponse) {
        // Vérifier si la question n'est pas null
        if (reponse != null) {
            // Récupérer la question existante de la base de données
            Reponse existingAnswer = reponseRepository.findById(reponse.getIdReponse()).orElse(null);
            // Si la question existe
            if (existingAnswer != null) {
                // Mettre à jour seulement le contenu si non null
                if (reponse.getContenu() != null) {
                    existingAnswer.setContenu(reponse.getContenu());
                }
                // Mettre à jour updatedAt
                existingAnswer.setUpdatedAt(LocalDateTime.now());
                // Enregistrer la question mise à jour dans la base de données
                return reponseRepository.save(existingAnswer);
            } else {
                // Gérer l'erreur si la question n'existe pas
                LOG.error(String.format(ERROR_NON_PRESENT_ID, reponse.getIdReponse()));
                // Vous pouvez lancer une exception appropriée ici si vous le souhaitez
                return null;
            }
        } else {
            // Gérer l'erreur si la question est null
            LOG.error(ERROR_NULL_ID);

            return null;
        }
    }

    @Override
    public void DeleteAnswer(Reponse reponse) {
        if (reponse != null) {
            reponseRepository.delete(reponse);}
        else {
            // Gérer l'erreur si la question est null
            LOG.error(ERROR_NON_PRESENT_ID);

        }
        if (reponse == null) {

            // Gérer l'erreur si la question est null
            LOG.error(ERROR_NULL_ID);


        }
    }

    @Override
    public List<Reponse> getAllAnswers() {
        return reponseRepository.findAll();
    }

    @Override
    public List<Reponse> getAllAnswersForQuestion(int idQuestion) {
        // Récupérer la question correspondante
        Question question = questionRepository.findById(idQuestion).orElse(null);

        // Vérifier si la question existe
        if (question == null) {
            LOG.error(String.format(ERROR_NON_PRESENT_ID, idQuestion));
            return Collections.emptyList(); // Retourner une liste vide en cas d'erreur
        }

        // Récupérer toutes les réponses associées à cette question
        return reponseRepository.findByQuestion(question);
    }
}


