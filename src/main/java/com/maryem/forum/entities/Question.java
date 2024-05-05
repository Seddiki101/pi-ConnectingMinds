package com.maryem.forum.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuestion;
    @JsonProperty("firstName")
    private String FirstName;
    @JsonProperty("lastName")
    private String LastName ;
    @JsonProperty("idUser")
    private Long IdUser;
    @Column(name = "nombre_likes")
    @JsonProperty("like")
    private Integer like;

    @NotBlank(message = "Le contenu ne peut pas être vide")
    @JsonProperty("contenu")
    private String Contenu;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;


    @Column(name = "image", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] image;
    public Question() {
        this.like = 0; // Initialise le champ "like" à 0 lors de la création d'une nouvelle instance
    }



    @OneToMany(mappedBy = "question" , cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Reponse> reponses ;


    public void addLike() {
        this.like++;
    }

    public void removeLike() {
        if (this.like > 0) {
            this.like--;
        }
    }



}