package com.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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
    private Long IdReservation;
    @ManyToOne
    @JoinColumn(name = "IdWorkshop")
    private Workshop workshop;
    private Date Date;
    private String Status;

    public Reservation() {
    }

    public Reservation(Long idReservation, Workshop workshop, java.util.Date date, String status) {
        IdReservation = idReservation;
        this.workshop = workshop;
        Date = date;
        Status = status;
    }

    public Long getIdReservation() {
        return IdReservation;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public String getStatus() {
        return Status;
    }


    public void setIdReservation(Long idReservation) {
        IdReservation = idReservation;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
