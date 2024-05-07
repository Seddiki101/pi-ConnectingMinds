package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectDao extends JpaRepository<Project,Long> {
    List<Project> findByOwnerId(Long ownerId);
    Optional<Project> findByName(String name);
    Optional<Project> findByTeamsTeamId(Long teamId);

    @Query("SELECT p FROM Project p " +
            "LEFT JOIN p.teams t " +
            "WHERE p.ownerId = :userId OR :userId IN (SELECT m FROM Team t2 JOIN t2.members m WHERE t2.project = p) " +
            "OR :userId IN (SELECT t3.scrumMaster FROM Team t3 WHERE t3.project = p)")
    List<Project> findProjectsByUserId(Long userId);
}
