package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamDao extends JpaRepository<Team,Long> {
    Optional<Team> findByName(String name);
}
