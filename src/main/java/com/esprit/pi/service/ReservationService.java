package com.esprit.pi.service;

import com.esprit.pi.entities.Reservation;

import java.util.List;
/**
 * Reservation Service Interface.
 */
public interface ReservationService {

    /**
     * Fetch the List of reservations.
     *
     * @return list of reservations.
     */
    public List<Reservation> findAll();
    /**
     * Fetch reservation by a given Id.
     *
     * @param id
     * @return reservation
     */
    public Reservation findById(Long id);


    /**
     * Updates a given reservation fields.
     *
     * @param reservation
     * @return updated reservation object.
     */
    public Reservation updateReservation(Reservation reservation);

    public void deleteReservation(Long id);

    Reservation ajouterReservation(Reservation reservation, Long idWorkshop );
}
