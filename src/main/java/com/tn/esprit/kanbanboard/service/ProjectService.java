package com.tn.esprit.kanbanboard.service;

import com.tn.esprit.kanbanboard.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();
    List<Project> findByOwnerId(Long ownerId);
    Optional<Project> findById(Long ID);
    Project create(Project project);
    Project update(Project project);
    void delete(Project project);
}
