package com.tn.esprit.kanbanboard.service;

import com.tn.esprit.kanbanboard.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> findAll();
    Optional<Team> findById(Long ID);
    Optional<Team> findByName(String name);
    Team create(Long projectId, Team team);
    Team update(Team team);
    void delete(Team team);
}
