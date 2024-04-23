package com.tn.esprit.kanbanboard.entity;

import com.tn.esprit.kanbanboard.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private Long ownerId;
    private String owner;
    private String name;
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    private String scope;
    private Date startDate;
    private Date endDate;
    private String resources;
    private String imageName;
    private String imageUrl;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Team> teams = new ArrayList<>();
}
