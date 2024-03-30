package com.tn.esprit.kanbanboard.service;

import com.tn.esprit.kanbanboard.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll();
    Optional<Event> findById(Long ID);
    Event create(Long groupId,Event event);
    Event update(Event event);
    void delete(Event event);
}
