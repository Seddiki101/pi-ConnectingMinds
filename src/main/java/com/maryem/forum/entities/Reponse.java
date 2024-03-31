package com.maryem.forum.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Getter
@Setter
public class Reponse  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReponse;
    @JsonProperty("firstName")
    private String FirstName;
    @JsonProperty("lastName")
    private String LastName ;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
    @NotBlank(message = "Le contenu ne peut pas être vide")
    @JsonProperty("contenu")
    private String Contenu;
    @ManyToOne
    @JsonBackReference
    private Question question;
    @Column(name = "image", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] image;
}
