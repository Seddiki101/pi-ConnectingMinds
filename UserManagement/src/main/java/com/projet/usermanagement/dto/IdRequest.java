package com.projet.usermanagement.dto;

import lombok.Data;

@Data
public class IdRequest {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
