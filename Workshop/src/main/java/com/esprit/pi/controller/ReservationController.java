package com.esprit.pi.controller;

import com.esprit.pi.entities.Reservation;
import com.esprit.pi.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller of reservation operations.
 */
@Controller
@RestController
@AllArgsConstructor
@RequestMapping("reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("get-all")
    public List<Reservation> getAllreservation(){
        return reservationService.findAll();
    }

    @GetMapping("get/{id}")
    public Reservation getReservationById(@PathVariable("id") Long id ){
        return id != null ? this.reservationService.findById(id) : null;
    }

    @PostMapping("add")
    public Reservation addReservation(@RequestBody Reservation reservation){
        return reservationService.addReservation(reservation);
    }

    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Reservation reservation){
        return reservation != null ? this.reservationService.updateReservation(reservation) : null;

    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
    }
}
