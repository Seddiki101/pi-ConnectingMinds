package com.esprit.pi.daos;

import com.esprit.pi.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Reservation DAO.
 */
@Repository
public interface ReservationDaos extends JpaRepository<Reservation, Long> {

}
