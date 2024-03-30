package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.GroupDao;
import com.tn.esprit.kanbanboard.dao.ProjectDao;
import com.tn.esprit.kanbanboard.entity.Group;
import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupDao groupDao;
    private final ProjectDao projectDao;
    @Autowired
    public GroupServiceImpl(GroupDao groupDao,ProjectDao projectDao) {
        this.groupDao = groupDao;
        this.projectDao = projectDao;
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAll();
    }

    @Override
    public Optional<Group> findById(Long ID) {
        return groupDao.findById(ID);
    }

    @Override
    public Group create(Long projectId,Group group) {
        Optional<Project> optionalProject = projectDao.findById(projectId);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            group.setProject(project);
            Group result = groupDao.save(group);
            return result;
        }
        System.out.println("Error : Project doesn't exist.");
        return null;
    }

    @Override
    public Group update(Group group) {
        Optional<Group> oldGroup = groupDao.findById(group.getGroupId());
        if(oldGroup.isPresent()){
            Group updatedGroup = oldGroup.get();
            updatedGroup.setName(group.getName());
            updatedGroup.setDescription(group.getDescription());
            updatedGroup.setScrumMaster(group.getScrumMaster());
            updatedGroup.setMembers(group.getMembers());
            Group result = groupDao.save(updatedGroup);
            return result;
        }
        System.out.println("Error : The Group doesn't exist to update.");
        return null;
    }

    @Override
    public void delete(Group group) {
        try {
            group.getMembers().clear();
            groupDao.save(group);
            Project project = group.getProject();
            project.getGroups().remove(group);
            projectDao.save(project);
        } catch (Exception e) {
            System.out.println("Error deleting group: " + e.getMessage());
        }
    }
}
