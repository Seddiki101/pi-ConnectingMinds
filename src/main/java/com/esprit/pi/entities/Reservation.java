package com.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Entity Class representing the reservations tables.
 */

@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    @ManyToOne
    @JoinColumn(name = "idWorkshop")
    private Workshop workshop;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String NomParticipant;
    private String PrenomParticipant;



    public Reservation() {
    }



}
