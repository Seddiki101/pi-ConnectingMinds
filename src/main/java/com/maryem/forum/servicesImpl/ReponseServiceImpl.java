package com.maryem.forum.servicesImpl;

import com.maryem.forum.daos.QuestionRepository;
import com.maryem.forum.daos.ReponseRepository;
import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import com.maryem.forum.services.BadWordsFilter;
import com.maryem.forum.services.ReponseService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class ReponseServiceImpl implements ReponseService {
    @Resource
    ReponseRepository reponseRepository;
    @Resource
    QuestionRepository questionRepository;
    @Autowired
    private BadWordsFilter badWordsFilter;
    private static final Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find an answer with id : %s";

    private static final String ERROR_UPDATE = "Error occured while updating";
    private void sendEmail(String to, String subject, String body) throws MessagingException {

        String from = "techwork414@gmail.com";
        String password = "pacrvzlvscatwwkb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        //   message.setText(body);
        message.setContent(body, "text/html");

        Transport.send(message);
    }

    @Override
    @Transactional
    public Reponse ajouterReponse(String contenu , int idQuestion, MultipartFile imageFile ,Long userID) {
        // Vérifier si le contenu de la réponse est null ou vide
        if (StringUtils.isEmpty(contenu)) {
            LOG.error("Posted answer content is NULL or empty");
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
        String filteredContenu = badWordsFilter.filter(contenu);
        newReponse.setContenu(filteredContenu);

        // Vérifier si une image a été fournie
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                newReponse.setImage(imageFile.getBytes());
            } catch (IOException e) {
                LOG.error("Error occurred while processing image file: " + e.getMessage());
                return null;
            }
        }


        // Associer la réponse à la question
        newReponse.setQuestion(question);

        // Mettre à jour la date de création si elle est nulle
        newReponse.setCreatedAt(LocalDateTime.now());

        if(userID != null ) {
            newReponse.setIdUser(userID);
        }



            String to = "zouaouimaryem327@gmail.com";
        String subject = "New answer Added";
        String body = "A new answer has been added with details:";

        try {
            sendEmail(to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // Enregistrer la réponse dans la base de données
        return reponseRepository.save(newReponse);

    }



    @Override
    public Reponse updateAnswer(Reponse reponse  ,Long userID) {
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
                // Mettre à jour l'image si une nouvelle image est fournie
                if (reponse.getImage() != null) {
                    existingAnswer.setImage(reponse.getImage());
                }
                // Mettre à jour updatedAt
                existingAnswer.setUpdatedAt(LocalDateTime.now());
                if(userID != null ) {
                    existingAnswer.setIdUser(userID);
                }
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
    public void DeleteAnswerById(int id) {
        reponseRepository.deleteById(id);
    }

    @Override
    public Reponse ReponseById(int id) {
        Reponse reponse = null;
        if (id!= 0) {
            final Optional<Reponse> optionalReponse= this.reponseRepository.findById(id);
            if (optionalReponse.isPresent()) {
                reponse = optionalReponse.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        } else {
            LOG.error(ERROR_NULL_ID);
        }
        return reponse;
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

    @Override
    public Reponse updateReponseById(Reponse reponse, int id) {
        Optional<Reponse>optionalReponse = reponseRepository.findById(id);
        if(optionalReponse.isPresent()){
            LocalDateTime modified = LocalDateTime.now();
            Reponse updatedReponse = optionalReponse.get();
            String filteredContenu = badWordsFilter.filter(reponse.getContenu());
            updatedReponse.setContenu(filteredContenu);
            //updatedQuestion.setImage(question.getImage());
            updatedReponse.setUpdatedAt(modified);
            //updatedQuestion.setImage(question.getImage());
            Reponse result = reponseRepository.save(updatedReponse);
            System.out.println("updated");
            return result;
        }
        System.out.println(">Reponse doesn't exist!!");

        return null;
    }

    @Override
    public List<Reponse> searchComments(String contenu) {
        return reponseRepository.findByContenu(contenu);
    }
}


