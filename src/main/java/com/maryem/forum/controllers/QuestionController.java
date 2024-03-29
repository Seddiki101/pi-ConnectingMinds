package com.maryem.forum.controllers;

import com.maryem.forum.entities.Question;
import com.maryem.forum.services.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Resource
    QuestionService questionService;
    @PostMapping("/AjouterQuestion")
    public Question ajoutQuestion(@RequestBody Question question){

       return questionService.ajouterQuestion(question);
    }
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
