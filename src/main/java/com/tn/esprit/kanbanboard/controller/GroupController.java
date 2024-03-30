package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Group;
import com.tn.esprit.kanbanboard.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {
    private final GroupService groupService;
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group")
    public ResponseEntity<List<Group>> getAllGroups(){
        return ResponseEntity.ok(groupService.findAll());
    }
    @GetMapping("/group/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable("id") Long id){
        Optional<Group> group = groupService.findById(id);
        return group.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/group/{id}")
    public ResponseEntity<Group> createGroup(@PathVariable("id") Long projectId, @RequestBody Group group){
        Group result = groupService.create(projectId,group);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @PutMapping("/group")
    public ResponseEntity<Group> updateGroup(@RequestBody Group group){
        Group result = groupService.update(group);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/group/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable("id") Long id){
        Optional<Group> optionalGroup = groupService.findById(id);
        if(optionalGroup.isPresent()){
            groupService.delete(optionalGroup.get());
            return ResponseEntity.ok("Group deleted successfully.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
