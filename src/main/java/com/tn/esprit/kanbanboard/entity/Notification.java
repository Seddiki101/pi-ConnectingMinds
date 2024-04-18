package com.tn.esprit.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isOpened;
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
