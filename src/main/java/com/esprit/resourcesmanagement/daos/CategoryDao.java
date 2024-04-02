package com.esprit.resourcesmanagement.daos;

import com.esprit.resourcesmanagement.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access Object of Category entity
 */
@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
}
