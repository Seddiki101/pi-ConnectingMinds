package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.ProjectDao;
import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;
    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Optional<Project> findById(Long ID) {
        return projectDao.findById(ID);
    }

    @Override
    public Project create(Project project) {
        return projectDao.save(project);
    }

    @Override
    public Project update(Project project) {
        Optional<Project> oldProject = projectDao.findById(project.getProjectId());
        if(oldProject.isPresent()){
            Project updatedProject = oldProject.get();
            updatedProject.setName(project.getName());
            updatedProject.setScope(project.getScope());
            updatedProject.setStartDate(project.getStartDate());
            updatedProject.setEndDate(project.getEndDate());
            updatedProject.setResources(project.getResources());
            return projectDao.save(updatedProject);
        }else{
            System.out.println("The Project doesn't exist to update.");
        }
        return null;
    }

    @Override
    public void delete(Project project) {
        projectDao.delete(project);
    }
}
