package com.esprit.resourcesmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entity representing the categories table.
 */
@Entity
@Getter
@Setter
@Table(name="categories")
public class Categorie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private long size;
    @Lob
    private byte[] content;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
    private int likes;
    private int dislikes;
    private int downloads;
}
