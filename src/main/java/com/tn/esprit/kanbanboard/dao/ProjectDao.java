package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao extends JpaRepository<Project,Long> {
    List<Project> findByOwnerId(Long ownerId);
}
