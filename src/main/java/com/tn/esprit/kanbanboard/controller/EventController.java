package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Event;
import com.tn.esprit.kanbanboard.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/event")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> list= eventService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id){
        Optional<Event> optionalEvent= eventService.findById(id);
        return optionalEvent.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
    @PostMapping("/event/{id}")
    public ResponseEntity<Event> createEvent(@PathVariable("id") Long teamId,@RequestBody Event event){
        Event result = eventService.create(teamId,event);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @PutMapping("/event")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event){
        Event result = eventService.update(event);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/event/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id){
        Optional<Event> optionalEvent = eventService.findById(id);
        if(optionalEvent.isPresent()){
            eventService.delete(optionalEvent.get());
            return ResponseEntity.ok("Event deleted successfully.");
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
