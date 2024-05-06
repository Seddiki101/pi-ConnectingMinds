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
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
}
