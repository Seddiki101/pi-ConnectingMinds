package com.esprit.resourcesmanagement.daos;

import com.esprit.resourcesmanagement.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data access Object of Resource entity
 */
@Repository
public interface ResourceDao extends JpaRepository<Resource, Long> {
    @Query("SELECT r FROM Resource r WHERE r.id = (SELECT MAX(r2.id) FROM Resource r2)")
    Optional<Resource> findLastAddedResource();
}
