package com.esprit.pi.daos;

import com.esprit.pi.entities.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * workshop DAO.
 */
public interface WorkshopDaos extends JpaRepository<Workshop,Long>{

}
