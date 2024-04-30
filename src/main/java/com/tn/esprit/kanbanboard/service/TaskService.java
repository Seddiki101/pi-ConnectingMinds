package com.tn.esprit.kanbanboard.service;

import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    Optional<Task> findById(Long ID);
    Task create(Long teamId,Task task);
    Task update(Task task);
    void delete(Task task);

    public List<Task> getUpcomingTasksByProject(Project project);

}
