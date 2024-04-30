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

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be today or later")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateDeb;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be today or later")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

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

    public Workshop() {
    }

}

