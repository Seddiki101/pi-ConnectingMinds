package com.maryem.forum.controllers;

import com.maryem.forum.entities.Question;
import com.maryem.forum.services.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.index;

@RestController
public class QuestionController {
    @Resource
    QuestionService questionService;



    @PostMapping("/AjouterQuestion")
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

    //@PostMapping("AjouterQuestionI")
    //public Question ajouterQuestionI(@RequestParam ("contenu") String contenu, @RequestParam ("file") MultipartFile file) {
      //  return questionService.ajouterQuestionI(contenu,file);
    //}
    @PutMapping("/updateQuestion")
    @ResponseBody
    public Question updateQuestion(@RequestBody Question question) {
        return question != null ? this.questionService.updateQuestion(question) : null;
    }

    @DeleteMapping("DeleteQuestion")
    @ResponseBody
    public void deleteQuestion(@RequestBody Question question){
        questionService.DeleteQuestion(question);
    }
    @GetMapping("/Questions")
    @ResponseBody
    public List<Question> getAllQuestions(){
        List<Question> questions =  questionService.getAllQuestion();
        return questions;
    }

}
