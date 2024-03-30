package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.GroupDao;
import com.tn.esprit.kanbanboard.dao.TaskDao;
import com.tn.esprit.kanbanboard.entity.Group;
import com.tn.esprit.kanbanboard.entity.Task;
import com.tn.esprit.kanbanboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;
    private final GroupDao groupDao;
    @Autowired
    public TaskServiceImpl(TaskDao taskDao,GroupDao groupDao) {
        this.taskDao = taskDao;
        this.groupDao = groupDao;
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
        Optional<Group> optionalGroup = groupDao.findById(groupId);
        if(optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            task.setGroup(group);
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
