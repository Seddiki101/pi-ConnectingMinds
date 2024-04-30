package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.entity.UploadedFile;
import com.tn.esprit.kanbanboard.service.BlobService;
import com.tn.esprit.kanbanboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;
    private final BlobService blobService;
    @Autowired
    public ProjectController(ProjectService projectService,BlobService blobService) {
        this.projectService = projectService;
        this.blobService = blobService;
    }

    @GetMapping("/project")
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/project/owner/{id}")
    public ResponseEntity<List<Project>> getAllProjectsByOwnerId(@PathVariable("id") Long id){
        List<Project> projects = projectService.findByOwnerId(id);
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/project/unique-names/{name}")
    public ResponseEntity<Project> getProjectByName(@PathVariable("name") String name){
        Optional<Project> project = projectService.findByName(name);
        return project.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Long id){
        Optional<Project> project = projectService.findById(id);
        return project.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping(value = "/project",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> createProject(@RequestPart("project") Project project,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        UploadedFile information = new UploadedFile();
        if (file != null && !file.isEmpty()) {
            information = blobService.storeFile(file.getOriginalFilename(),file.getInputStream(), file.getSize());
            if(information.getUniqueFileName() != null && information.getUrl() != null){
                project.setImageName(information.getUniqueFileName());
                project.setImageUrl(information.getUrl());
            }
        }
        Project result = projectService.create(project);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping(value = "/project",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> updateProject(@RequestPart("project") Project project,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        UploadedFile information = new UploadedFile();
        if (file != null && !file.isEmpty()) {
            if(project.getImageName() == null && project.getImageUrl() == null){
                information = blobService.storeFile(file.getOriginalFilename(),file.getInputStream(), file.getSize());
            }else{
                information = blobService.updateFile(project.getImageName(),file.getOriginalFilename(),file.getInputStream(), file.getSize());
            }
            if(information.getUniqueFileName() != null && information.getUrl() != null){
                project.setImageName(information.getUniqueFileName());
                project.setImageUrl(information.getUrl());
            }
        }
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
