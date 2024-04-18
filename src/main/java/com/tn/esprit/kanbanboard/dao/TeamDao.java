package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamDao extends JpaRepository<Team,Long> {
}
