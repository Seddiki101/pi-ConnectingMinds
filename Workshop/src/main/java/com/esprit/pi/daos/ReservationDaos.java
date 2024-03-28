package com.esprit.pi.daos;

import com.esprit.pi.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * Reservation DAO.
 */
public interface ReservationDaos extends JpaRepository<Reservation, Long> {

}
