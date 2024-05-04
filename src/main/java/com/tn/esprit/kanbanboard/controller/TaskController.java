package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.entity.Task;
import com.tn.esprit.kanbanboard.service.ProjectService;
import com.tn.esprit.kanbanboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    @Autowired
    public TaskController(TaskService taskService,ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }
    @GetMapping("/task")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> list = taskService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id){
        Optional<Task> optionalTask = taskService.findById(id);
        return optionalTask.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
    @GetMapping("/task/project/{id}")
    public ResponseEntity<List<Task>> getUpcomingTasksByProjectId(@PathVariable("id") Long projectId){
        Optional<Project> optionalProject = projectService.findById(projectId);
        if(optionalProject.isPresent()){
            List<Task> result = taskService.getUpcomingTasksByProject(optionalProject.get());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/task/{id}")
    public ResponseEntity<Task> createTask(@PathVariable("id") Long teamId,@RequestBody Task task){
        Task result = taskService.create(teamId, task);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @PutMapping("/task")
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        Task result = taskService.update(task);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id){
        Optional<Task> optionalTask = taskService.findById(id);
        if(optionalTask.isPresent()){
            taskService.delete(optionalTask.get());
            return ResponseEntity.ok("Task deleted Successfully.");
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
