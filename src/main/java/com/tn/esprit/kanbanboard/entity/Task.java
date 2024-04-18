package com.tn.esprit.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tn.esprit.kanbanboard.enums.Priority;
import com.tn.esprit.kanbanboard.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime deadLine;
    private Long memberId;
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Team team;

    @JsonGetter("groupId")
    public Long getGroupId() {
        return (team != null) ? team.getGroupId() : null;
    }
}
