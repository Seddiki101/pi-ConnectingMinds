package com.maryem.forum.servicesImpl;

import com.maryem.forum.daos.QuestionRepository;
import com.maryem.forum.entities.Question;
import com.maryem.forum.services.QuestionService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.id.SequenceMismatchStrategy.LOG;


@Service

public class QuestionServiceImpl implements QuestionService {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a question with id : %s";

    private static final String ERROR_UPDATE = "Error occured while updating";
    @Resource
    QuestionRepository questionRepository;
    @Override
    public Question ajouterQuestion(Question question) {
        if (question != null) {
            // Créer une nouvelle instance de Question pour éviter de modifier l'objet d'origine
            Question newQuestion = new Question();

            // Copier les attributs nécessaires de la question fournie
            newQuestion.setContenu(question.getContenu());

            // Initialiser createdAt si nécessaire
            if (question.getCreatedAt() == null) {
                newQuestion.setCreatedAt(LocalDateTime.now());
            }

            // Enregistrer et retourner la nouvelle question avec uniquement le contenu
            return questionRepository.save(newQuestion);
        } else {
            // Gérer l'erreur si la question est null
            LOG.error(ERROR_NULL_ID);
            return null;
        }
    }

    @Override
    public Question updateQuestion(Question question) {
        // Vérifier si la question n'est pas null
        if (question != null) {
            // Récupérer la question existante de la base de données
            Question existingQuestion = questionRepository.findById(question.getIdQuestion()).orElse(null);
            // Si la question existe
            if (existingQuestion != null) {
                // Mettre à jour seulement le contenu si non null
                if (question.getContenu() != null) {
                    existingQuestion.setContenu(question.getContenu());
                }
                // Mettre à jour updatedAt
                existingQuestion.setUpdatedAt(LocalDateTime.now());
                // Enregistrer la question mise à jour dans la base de données
                return questionRepository.save(existingQuestion);
            } else {
                // Gérer l'erreur si la question n'existe pas
                LOG.error(String.format(ERROR_NON_PRESENT_ID, question.getIdQuestion()));
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
    public void DeleteQuestion(Question question) {
        if (question != null) {
        questionRepository.delete(question);}
           else {
            // Gérer l'erreur si la question est null
            LOG.error(ERROR_NON_PRESENT_ID);

            }
        if (question == null) {

            // Gérer l'erreur si la question est null
            LOG.error(ERROR_NULL_ID);


        }

    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }




}

