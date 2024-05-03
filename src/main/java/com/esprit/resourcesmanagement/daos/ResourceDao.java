package com.esprit.resourcesmanagement.daos;

import com.esprit.resourcesmanagement.entities.Resource;
import com.esprit.resourcesmanagement.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data access Object of Resource entity
 */
@Repository
public interface ResourceDao extends JpaRepository<Resource, Long> {
    @Query("SELECT r FROM Resource r WHERE r.resourceId = (SELECT MAX(r2.resourceId) FROM Resource r2)")
    Optional<Resource> findLastAddedResource();

    List<Resource> findByUserId(Long userId);


}
