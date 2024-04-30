package com.esprit.pi.controller;

import com.esprit.pi.entities.Reservation;
import com.esprit.pi.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("listRes")
    public List<Reservation> getAllreservation(){
        return reservationService.findAll();
    }

    @GetMapping("get/{id}")
    public Reservation getReservationById(@PathVariable("id") Long id ){
        return id != null ? this.reservationService.findById(id) : null;
    }
    @PostMapping("/addReservation/{idWorkshop}")
    public Reservation ajouterReservation(@RequestBody Reservation reservation,@PathVariable("idWorkshop") Long idWorkshop){
        return reservationService.ajouterReservation(reservation, idWorkshop);
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
