package com.maryem.forum.controllers;

import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import com.maryem.forum.services.ReponseService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ReponseController {
    @Resource
    ReponseService reponseService;
    @GetMapping("/searchR")
    public List<Reponse> searchPosts(@RequestParam("contenu") String contenu) {
        return reponseService.searchComments(contenu);
    }

    @PutMapping("/updateReponse/{id}")
    public ResponseEntity<Reponse> updateReponseById(@PathVariable("id") int id,
                                                       @RequestParam("contenu") String contenu,
                                                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        // Récupérer la question existante
        Optional<Reponse> optionalReponse = Optional.ofNullable(reponseService.ReponseById(id));
        if (optionalReponse.isPresent()) {
            Reponse reponse = optionalReponse.get();
            // Mettre à jour le contenu s'il est fourni
            if (contenu != null) {
                reponse.setContenu(contenu);
            }

            // Mettre à jour la question dans la base de données
            Reponse updatedReponse = reponseService.updateReponseById(reponse, id);
            return ResponseEntity.ok(updatedReponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/addResponse/{idQuestion}")
    public Reponse ajouterReponse(
            @RequestParam String contenu,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @PathVariable int idQuestion
    ) {
        return reponseService.ajouterReponse(contenu, idQuestion, imageFile);
    }




    @PutMapping("/updateReponse")
    @ResponseBody
    public Reponse updateReponse(@RequestBody Reponse reponse) {
        return reponse != null ? this.reponseService.updateAnswer(reponse) : null;
    }
    @DeleteMapping("DeleteAnswer/{id}")
    @ResponseBody
    public void deleteAnswer(@PathVariable("id") int id){
        reponseService.DeleteAnswerById(id);
    }

    @GetMapping("/getReponseById/{id}")
    @ResponseBody
    public ResponseEntity<Reponse> getReponseById(@PathVariable int id) {
        // Récupérer la question par son identifiant
        Optional<Reponse> optionalReponse = Optional.ofNullable(this.reponseService.ReponseById(id));
        // Vérifier si la question existe
        if (optionalReponse.isPresent()) {
            // Si oui, renvoyer la question avec un statut 200 OK
            return ResponseEntity.ok(optionalReponse.get());
        } else {
            // Si la question n'existe pas, renvoyer une réponse 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("DeleteAnswer")
    @ResponseBody
    public void deleteAnswer(@RequestBody Reponse reponse){
        reponseService.DeleteAnswer(reponse);
    }
    @GetMapping("/Answers")
    @ResponseBody
    public List<Reponse> getAllAnswers(){
        List<Reponse> answers =  reponseService.getAllAnswers();
        return answers;
    }
    @GetMapping("/AnswersByQuestion/{idQuestion}")
    @ResponseBody
    public List<Reponse> getAllAnswersForQuestion(@PathVariable int idQuestion){
        return reponseService.getAllAnswersForQuestion(idQuestion);
    }

}
