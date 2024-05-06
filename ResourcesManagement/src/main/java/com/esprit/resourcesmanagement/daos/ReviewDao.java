package com.esprit.resourcesmanagement.daos;

import com.esprit.resourcesmanagement.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access Object of Review entity
 */
@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {
    List<Review> findByResource_ResourceId(Long resourceId);
}
