package com.esprit.resourcesmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="reviews")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String content ;

    private Long userId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "resource_id")
    private Resource resource;
}
