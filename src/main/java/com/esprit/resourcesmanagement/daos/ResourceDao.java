package com.esprit.resourcesmanagement.daos;

import com.esprit.resourcesmanagement.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access Object of Resource entity
 */
@Repository
public interface ResourceDao extends JpaRepository<Resource, Long> {
}
