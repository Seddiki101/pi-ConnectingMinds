package com.esprit.pi.dto;

import lombok.Data;

import java.util.Date;

/**
 * Create Workshop DTO.
 */
@Data
public class WorkshopDto {
    private String title;
    private String description;
    private Date dateDeb;
    private Date dateFin;
    private Double prix;
    private int maxCapacity;
    private int currentCapacity;
    private String localisation;
}
