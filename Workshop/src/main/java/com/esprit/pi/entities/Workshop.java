package com.esprit.pi.entities;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity Class representing the workshops tables.
 */
@Entity
@Getter
@Setter
@Table(name = "workshops")
public class Workshop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdWorkshop;
    private String Title ;
    private String Description;
    private Date DateDeb;
    private Date DateFin;
    private Double prix;
    private int MaxCapacity;
    private int CurrentCapacity;
    private String Localisation;

    @ManyToOne
    @JoinColumn(name="IdReservation")
    private Reservation reservation;

    public Workshop() {
    }

    public Workshop(Long idWorkshop, String title, String description, Date dateDeb, Date dateFin, Double prix, int maxCapacity, int currentCapacity, String localisation, Reservation reservation) {
        IdWorkshop = idWorkshop;
        Title = title;
        Description = description;
        DateDeb = dateDeb;
        DateFin = dateFin;
        this.prix = prix;
        MaxCapacity = maxCapacity;
        CurrentCapacity = currentCapacity;
        Localisation = localisation;
        this.reservation = reservation;
    }

    public Long getIdWorkshop() {
        return IdWorkshop;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public Date getDateDeb() {
        return DateDeb;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public Double getPrix() {
        return prix;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public int getCurrentCapacity() {
        return CurrentCapacity;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setIdWorkshop(Long idWorkshop) {
        IdWorkshop = idWorkshop;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDateDeb(Date dateDeb) {
        DateDeb = dateDeb;
    }

    public void setDateFin(Date dateFin) {
        DateFin = dateFin;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void setMaxCapacity(int maxCapacity) {
        MaxCapacity = maxCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        CurrentCapacity = currentCapacity;
    }

    public void setLocalisation(String localisation) {
        Localisation = localisation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
