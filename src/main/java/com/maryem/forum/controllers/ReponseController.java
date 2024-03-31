package com.maryem.forum.controllers;

import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import com.maryem.forum.services.ReponseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ReponseController {
    @Resource
    ReponseService reponseService;
    @PostMapping("/addResponse/{idQuestion}")
    public Reponse ajouterReponse(
            @RequestParam("contenu") String contenu,
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
