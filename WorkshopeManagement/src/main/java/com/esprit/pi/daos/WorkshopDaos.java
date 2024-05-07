package com.esprit.pi.daos;

import com.esprit.pi.entities.Reservation;
import com.esprit.pi.entities.Workshop;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;


/**
 * workshop DAO.
 */
@Repository
public interface WorkshopDaos extends JpaRepository<Workshop,Long>{

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.workshop.idWorkshop = :workshopId")
    Long countReservationsByWorkshopId(Long workshopId);
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.workshop.idWorkshop = :workshopId")
    long countByWorkshopId(Long workshopId);

}
