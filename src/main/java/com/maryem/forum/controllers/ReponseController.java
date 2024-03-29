package com.maryem.forum.controllers;

import com.maryem.forum.entities.Question;
import com.maryem.forum.entities.Reponse;
import com.maryem.forum.services.ReponseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReponseController {
    @Resource
    ReponseService reponseService;
    @PostMapping("/addResponse/{idQuestion}")
    public Reponse ajouterReponse(@RequestBody Reponse reponse, @PathVariable int idQuestion) {
        return reponseService.ajouterReponse(reponse, idQuestion);
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

}
