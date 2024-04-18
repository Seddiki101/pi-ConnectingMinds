package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Team;
import com.tn.esprit.kanbanboard.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;
    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/team")
    public ResponseEntity<List<Team>> getAllteams(){
        return ResponseEntity.ok(teamService.findAll());
    }
    @GetMapping("/team/{id}")
    public ResponseEntity<Team> getteamById(@PathVariable("id") Long id){
        Optional<Team> team = teamService.findById(id);
        return team.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/team/{id}")
    public ResponseEntity<Team> createteam(@PathVariable("id") Long projectId, @RequestBody Team team){
        Team result = teamService.create(projectId, team);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @PutMapping("/team")
    public ResponseEntity<Team> updateteam(@RequestBody Team team){
        Team result = teamService.update(team);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/team/{id}")
    public ResponseEntity<String> deleteteam(@PathVariable("id") Long id){
        Optional<Team> optionalteam = teamService.findById(id);
        if(optionalteam.isPresent()){
            teamService.delete(optionalteam.get());
            return ResponseEntity.ok("team deleted successfully.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
