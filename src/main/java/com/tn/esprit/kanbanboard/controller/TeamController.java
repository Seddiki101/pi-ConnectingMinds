package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Team;
import com.tn.esprit.kanbanboard.entity.UploadedFile;
import com.tn.esprit.kanbanboard.service.BlobService;
import com.tn.esprit.kanbanboard.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;
    private final BlobService blobService;
    @Autowired
    public TeamController(TeamService teamService,BlobService blobService) {
        this.teamService = teamService;
        this.blobService = blobService;
    }

    @GetMapping("/team")
    public ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamService.findAll());
    }
    @GetMapping("/team/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id){
        Optional<Team> team = teamService.findById(id);
        return team.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
    @GetMapping("/team/unique-names/{name}")
    public ResponseEntity<Team> getTeamByName(@PathVariable("name") String name){
        Optional<Team> team = teamService.findByName(name);
        return team.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
    @PostMapping(value="/team/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Team> createTeam(@PathVariable("id") Long projectId, @RequestPart("team") Team team, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        UploadedFile information = new UploadedFile();
        if (file != null && !file.isEmpty()) {
            information = blobService.storeFile(file.getOriginalFilename(),file.getInputStream(), file.getSize());
            if(information.getUniqueFileName() != null && information.getUrl() != null){
                team.setImageName(information.getUniqueFileName());
                team.setImageUrl(information.getUrl());
            }
        }
        Team result = teamService.create(projectId, team);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @PutMapping(value="/team",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Team> updateTeam(@RequestPart("team") Team team, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        UploadedFile information = new UploadedFile();
        if (file != null && !file.isEmpty()) {
            if(team.getImageName() == null && team.getImageUrl() == null){
                information = blobService.storeFile(file.getOriginalFilename(),file.getInputStream(), file.getSize());
            }else{
                information = blobService.updateFile(team.getImageName(),file.getOriginalFilename(),file.getInputStream(), file.getSize());
            }
            if(information.getUniqueFileName() != null && information.getUrl() != null){
                team.setImageName(information.getUniqueFileName());
                team.setImageUrl(information.getUrl());
            }
        }
        Team result = teamService.update(team);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/team/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable("id") Long id){
        Optional<Team> optionalteam = teamService.findById(id);
        if(optionalteam.isPresent()){
            teamService.delete(optionalteam.get());
            return ResponseEntity.ok("team deleted successfully.");
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
