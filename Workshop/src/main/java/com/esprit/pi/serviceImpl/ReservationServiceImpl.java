package com.esprit.pi.serviceImpl;

import com.esprit.pi.daos.ReservationDaos;
import com.esprit.pi.entities.Reservation;
import com.esprit.pi.service.ReservationService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation class of Reservation services.
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    /**
     * Internal Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ReservationServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a reservation with id : %s";

    private static final String ERROR_UPDATE = "Error occured while updating";


    @Resource
    private ReservationDaos reservationDaos;


    @Override
    public List<Reservation> findAll() {
        return reservationDaos.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        Reservation reservation= null;
        if ( id!= null) {
            final Optional<Reservation> optionalReservation = this.reservationDaos.findById(id);
            if (optionalReservation.isPresent()) {
                reservation = optionalReservation.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        }
        else {
            LOG.error(ERROR_NULL_ID);
        }
        return reservation;
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return this.reservationDaos.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        Reservation updateReservation=null;
        if (reservation!= null){
            updateReservation = this.reservationDaos.save(reservation);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return updateReservation;
    }

    @Override
    public void deleteReservation(Long id) {
        reservationDaos.deleteById(id);
    }
}
