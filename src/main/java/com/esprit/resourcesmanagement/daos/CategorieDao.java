package com.esprit.resourcesmanagement.daos;

import com.esprit.resourcesmanagement.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access Object of Categorie entity
 */
@Repository
public interface CategorieDao extends JpaRepository<Categorie, Long> {
}
