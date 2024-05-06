package com.esprit.pi.controller;

import com.esprit.pi.entities.Reservation;
import com.esprit.pi.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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

    @GetMapping("/workshops/{idWorkshop}")
    public ResponseEntity<List<Reservation>> getReservationsForWorkshop(@PathVariable Long idWorkshop) {
        List<Reservation> reservations = reservationService.getReservationsForWorkshop(idWorkshop);
        return ResponseEntity.ok(reservations);
    }
    @PostMapping("/addReservation/{idWorkshop}")
    public Reservation ajouterReservation(@RequestHeader("Authorization") String token ,@RequestBody Reservation reservation,@PathVariable("idWorkshop") Long idWorkshop){

        Long userId = getUserIdFromUserService(token);

        if(userId != null ) {  reservation.setUser_id(userId);}
        System.out.println("hehehe"+userId);

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


    public Long getUserIdFromUserService(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Long> response = restTemplate().exchange(
                "http://localhost:8082/api/v2/user/back/getUserSpot", HttpMethod.GET, entity, Long.class);

        return response.getBody();
    }

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}
