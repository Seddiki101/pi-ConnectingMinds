package com.tn.esprit.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private String name;
    private String description;
    private Long scrumMaster;
    @ElementCollection
    private List<Long> members = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Event> events = new ArrayList<>();
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @JsonGetter("projectId")
    public Long getProjectId() {
        return (project != null) ? project.getProjectId() : null;
    }
}
