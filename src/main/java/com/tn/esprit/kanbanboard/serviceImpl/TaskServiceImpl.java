package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.TeamDao;
import com.tn.esprit.kanbanboard.dao.TaskDao;
import com.tn.esprit.kanbanboard.entity.Team;
import com.tn.esprit.kanbanboard.entity.Task;
import com.tn.esprit.kanbanboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;
    private final TeamDao teamDao;
    @Autowired
    public TaskServiceImpl(TaskDao taskDao, TeamDao teamDao) {
        this.taskDao = taskDao;
        this.teamDao = teamDao;
    }

    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }

    @Override
    public Optional<Task> findById(Long ID) {
        return taskDao.findById(ID);
    }

    @Override
    public Task create(Long groupId,Task task) {
        Optional<Team> optionalGroup = teamDao.findById(groupId);
        if(optionalGroup.isPresent()){
            Team team = optionalGroup.get();
            task.setTeam(team);
            Task result = taskDao.save(task);
            return result;
        }
        System.out.println("Error : Group doesn't exist.");
        return null;
    }

    @Override
    public Task update(Task task) {
        Optional<Task> optionalTask = taskDao.findById(task.getTaskId());
        if(optionalTask.isPresent()){
            Task updatedTask = optionalTask.get();
            updatedTask.setName(task.getName());
            updatedTask.setCreatedAt(task.getCreatedAt());
            updatedTask.setDeadLine(task.getDeadLine());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setStatus(task.getStatus());
            updatedTask.setPriority(task.getPriority());
            Task result = taskDao.save(updatedTask);
            return result;
        }
        System.out.println("Error : Task doesn't exist.");
        return null;
    }

    @Override
    public void delete(Task task) {
        taskDao.delete(task);
    }
}
