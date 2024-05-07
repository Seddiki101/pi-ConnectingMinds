package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.TeamDao;
import com.tn.esprit.kanbanboard.dao.ProjectDao;
import com.tn.esprit.kanbanboard.entity.Team;
import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamDao teamDao;
    private final ProjectDao projectDao;
    @Autowired
    public TeamServiceImpl(TeamDao teamDao, ProjectDao projectDao) {
        this.teamDao = teamDao;
        this.projectDao = projectDao;
    }

    @Override
    public List<Team> findAll() {
        return teamDao.findAll();
    }

    @Override
    public Optional<Team> findById(Long ID) {
        return teamDao.findById(ID);
    }
    @Override
    public Optional<Team> findByName(String name) {
        return teamDao.findByName(name);
    }

    @Override
    public Optional<Team> findByTaskId(Long taskId) {
        return teamDao.findByTasksTaskId(taskId);
    }

    @Override
    public Team create(Long projectId, Team team) {
        Optional<Project> optionalProject = projectDao.findById(projectId);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            team.setProject(project);
            Team result = teamDao.save(team);
            return result;
        }
        System.out.println("Error : Project doesn't exist.");
        return null;
    }

    @Override
    public Team update(Team team) {
        Optional<Team> oldTeam = teamDao.findById(team.getTeamId());
        if(oldTeam.isPresent()){
            Team updatedTeam = oldTeam.get();
            updatedTeam.setName(team.getName());
            updatedTeam.setDescription(team.getDescription());
            updatedTeam.setScrumMaster(team.getScrumMaster());
            updatedTeam.setMembers(team.getMembers());
            updatedTeam.setImageName(team.getImageName());
            updatedTeam.setImageUrl(team.getImageUrl());
            Team result = teamDao.save(updatedTeam);
            return result;
        }
        System.out.println("Error : The Team doesn't exist to update.");
        return null;
    }

    @Override
    public void delete(Team team) {
        try {
            team.getMembers().clear();
            teamDao.save(team);
            Project project = team.getProject();
            project.getTeams().remove(team);
            projectDao.save(project);
        } catch (Exception e) {
            System.out.println("Error deleting Team: " + e.getMessage());
        }
    }
}
