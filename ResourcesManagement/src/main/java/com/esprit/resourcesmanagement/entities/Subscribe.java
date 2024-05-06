package com.esprit.resourcesmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="subscribes")
public class Subscribe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscribeId;

    private String email;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;
}
