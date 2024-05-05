package com.maryem.forum.servicesImpl;

import com.maryem.forum.daos.QuestionRepository;
import com.maryem.forum.entities.Question;
import com.maryem.forum.services.BadWordsFilter;
import com.maryem.forum.services.QuestionService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import java.util.NoSuchElementException;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.hibernate.id.SequenceMismatchStrategy.LOG;


@Service

public class QuestionServiceImpl implements QuestionService {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a question with id : %s";

    private static final String ERROR_UPDATE = "Error occured while updating";
    @Resource
    QuestionRepository questionRepository;

    @Autowired
    private BadWordsFilter badWordsFilter;


    private int like;

    // Constructeurs, getters, setters et autres annotations...

    @Override
    public void addLikeToQuestion(int questionId) {
        // Récupérer la question à partir de l'ID
        Question question = questionRepository.findById(questionId)
                .orElse(null);

        // Ajouter un like à la question
        question.addLike();

        // Enregistrer les modifications dans la base de données
        questionRepository.save(question);
    }
    @Override

    // Méthode pour supprimer un like d'une question
    public void removeLikeFromQuestion(int questionId) {
        // Récupérer la question à partir de l'ID
        Question question = questionRepository.findById(questionId).orElse(null);


        // Supprimer un like de la question
        question.removeLike();

        // Enregistrer les modifications dans la base de données
        questionRepository.save(question);
    }

    @Override
    public Question ajouterQuestion(String contenu, MultipartFile imageFile) throws IOException {
        if (!StringUtils.isEmpty(contenu)) {
            Question newQuestion = new Question();
            String filteredContenu = badWordsFilter.filter(contenu);
            newQuestion.setContenu(filteredContenu);

            // Vérifier si une image a été fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                newQuestion.setImage(imageFile.getBytes());
            }

            if (newQuestion.getCreatedAt() == null) {
                newQuestion.setCreatedAt(LocalDateTime.now());
            }

            return questionRepository.save(newQuestion);
        } else {
            LOG.error("Le contenu ne peut pas être vide");
            return null;
        }


    }



  /*  @Override
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
                // Mettre à jour l'image si une nouvelle image est fournie
                if (question.getImage() != null) {
                    existingQuestion.setImage(question.getImage());
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
    }*/


    @Override
    public void DeleteQuestion(int id) {
        questionRepository.deleteById(id);

    }




    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Question QuestionById(int id) {
        Question question = null;
        if (id!= 0) {
            final Optional<Question> optionalQuestion = this.questionRepository.findById(id);
            if (optionalQuestion.isPresent()) {
                question = optionalQuestion.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        } else {
            LOG.error(ERROR_NULL_ID);
        }
        return question;
    }

    @Override
    public Question updateQuestionById(Question question, int id) {
        Optional<Question>optionalQuestion = questionRepository.findById(id);
        if(optionalQuestion.isPresent()){
            LocalDateTime modified = LocalDateTime.now();
            Question updatedQuestion = optionalQuestion.get();
            //updatedQuestion.setContenu(question.getContenu());
            String filteredContenu = badWordsFilter.filter(question.getContenu());
            updatedQuestion.setContenu(filteredContenu);
            //updatedQuestion.setImage(question.getImage());
            updatedQuestion.setUpdatedAt(modified);
            //updatedQuestion.setImage(question.getImage());
            Question result = questionRepository.save(updatedQuestion);
            System.out.println("updated");
            return result;
        }
        System.out.println(">Question doesn't exist!!");

        return null;
    }

    @Override
    public List<Question> searchPosts(String contenu) {
        return questionRepository.findByContenu(contenu);
    }

    //@Override
    //public Question ajouterQuestionI(String contenu, MultipartFile file) {
    //Question p =new Question();
    /// String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    // p.setContenu(contenu);
    // try {
    //   p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
    // } catch (IOException e) {
    //  throw new RuntimeException(e);
    //}
    //  return questionRepository.save(p);
    //  }


}