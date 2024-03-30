package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/project")
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Long id){
        Optional<Project> project = projectService.findById(id);
        return project.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/project")
    public  ResponseEntity<Project> createProject(@RequestBody Project project){
        Project result = projectService.create(project);
        if(result!=null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/project")
    public ResponseEntity<Project> updateProject(@RequestBody Project project){
        Project result = projectService.update(project);
        if(result!=null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id){
        Optional<Project> toDelete = projectService.findById(id);
        if(toDelete.isPresent()){
            projectService.delete(toDelete.get());
            return ResponseEntity.ok("Project deleted successfully.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
