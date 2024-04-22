package com.maryem.forum.controllers;

import com.maryem.forum.entities.Question;
import com.maryem.forum.services.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.index;

@RestController
@CrossOrigin
public class QuestionController {
    @Resource
    QuestionService questionService;



    @PostMapping("/AjouterQuestion")
    @ResponseBody
    public Question ajouterQuestion(
            @RequestParam("contenu") String contenu,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        try {
            return questionService.ajouterQuestion(contenu, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Gérer l'exception d'une manière appropriée
        }
    }
    @GetMapping("/Questions/{id}/image")
    public ResponseEntity<byte[]> getImageForQuestion(@PathVariable int id) {
        Optional<Question> optionalQuestion = Optional.ofNullable(questionService.QuestionById(id));
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            if (question.getImage() != null && question.getImage().length > 0) {
                // Renvoyer l'image sous forme de réponse avec le type de contenu approprié
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(question.getImage());
            } else {
                // Renvoyer une réponse indiquant que l'image n'est pas disponible pour ce post
                return ResponseEntity.notFound().build();
            }
        } else {
            // Renvoyer une réponse indiquant que le post n'existe pas
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/updateQuestion/{id}")
    public ResponseEntity<Question> updateQuestionById(@PathVariable("id") int id,
                                                       @RequestParam("contenu") String contenu,
                                                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        // Récupérer la question existante
        Optional<Question> optionalQuestion = Optional.ofNullable(questionService.QuestionById(id));
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            // Mettre à jour le contenu s'il est fourni
            if (contenu != null) {
                question.setContenu(contenu);
            }
            // Mettre à jour l'image si elle est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    question.setImage(imageFile.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer l'erreur d'une manière appropriée
                }
            }
            // Mettre à jour la question dans la base de données
            Question updatedQuestion = questionService.updateQuestionById(question, id);
            return ResponseEntity.ok(updatedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @DeleteMapping("DeleteQuestion/{id}")
    @ResponseBody
    public void deleteQuestion(@PathVariable("id") int id){
        questionService.DeleteQuestion(id);
    }
    @GetMapping("/Questions")
    @ResponseBody
    public List<Question> getAllQuestions(){
        List<Question> questions =  questionService.getAllQuestion();
        return questions;
    }

    @GetMapping("/getQuestionById/{id}")
    @ResponseBody
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        // Récupérer la question par son identifiant
        Optional<Question> optionalQuestion = Optional.ofNullable(this.questionService.QuestionById(id));
        // Vérifier si la question existe
        if (optionalQuestion.isPresent()) {
            // Si oui, renvoyer la question avec un statut 200 OK
            return ResponseEntity.ok(optionalQuestion.get());
        } else {
            // Si la question n'existe pas, renvoyer une réponse 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }



}