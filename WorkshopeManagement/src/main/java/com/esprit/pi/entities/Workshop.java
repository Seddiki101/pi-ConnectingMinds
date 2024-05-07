package com.esprit.pi.entities;
import com.fasterxml.jackson.annotation.JsonFormat;


import com.esprit.pi.controller.WorkshopController;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Description;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

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
    private Long idWorkshop;

    @NotBlank(message = "Title cannot be empty")
    private String title ;

    @NotBlank(message = "description cannot be empty")
    private String description;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateDeb;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateFin;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date(); // Initialize here


    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double prix;

    @Min(value = 1, message = "Maximum capacity must be at least 1")
    private int maxCapacity;

    @Min(value = 0, message = "Current capacity must be non-negative")
    private int currentCapacity;

    @NotBlank(message = "Location cannot be empty")
    private String localisation;

    @Column(name = "image", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] image;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    private  Long user_id;

    public Workshop() {
    }

}

