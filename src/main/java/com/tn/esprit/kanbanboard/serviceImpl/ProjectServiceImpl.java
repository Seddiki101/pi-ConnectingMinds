package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.ProjectDao;
import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.service.BlobService;
import com.tn.esprit.kanbanboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;
    private final BlobService blobService;
    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao,BlobService blobService) {
        this.projectDao = projectDao;
        this.blobService = blobService;
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public List<Project> findByOwnerId(Long ownerId) {
        return projectDao.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Project> findByName(String name) {
        return  projectDao.findByName(name);
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
            updatedProject.setProjectStatus(project.getProjectStatus());
            updatedProject.setImageName(project.getImageName());
            updatedProject.setImageUrl(project.getImageUrl());
            return projectDao.save(updatedProject);
        }else{
            System.out.println("The Project doesn't exist to update.");
        }
        return null;
    }

    @Override
    public void delete(Project project) {
        if(project.getImageName() != null && !project.getImageName().isEmpty()){ //delete file from the cloud container for a better storage capacity
            blobService.deleteFile(project.getImageName());
        }
        projectDao.delete(project);
    }
}
