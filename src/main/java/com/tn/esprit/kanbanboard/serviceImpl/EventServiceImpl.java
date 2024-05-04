package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.EventDao;
import com.tn.esprit.kanbanboard.dao.TeamDao;
import com.tn.esprit.kanbanboard.entity.Event;
import com.tn.esprit.kanbanboard.entity.Team;
import com.tn.esprit.kanbanboard.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final TeamDao teamDao;
    @Autowired
    public EventServiceImpl(EventDao eventDao, TeamDao teamDao) {
        this.eventDao = eventDao;
        this.teamDao = teamDao;
    }

    @Override
    public List<Event> findAll() {
        return eventDao.findAll();
    }

    @Override
    public Optional<Event> findById(Long ID) {
        return eventDao.findById(ID);
    }

    @Override
    public Event create(Long teamId,Event event) {
        Optional<Team> optionalTeam = teamDao.findById(teamId);
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            event.setTeam(team);
            Event result = eventDao.save(event);
            return result;
        }
        System.out.println("Error : Team doesn't exist.");
        return null;
    }

    @Override
    public Event update(Event event) {
       Optional<Event> optionalEvent = eventDao.findById(event.getEventId());
         if(optionalEvent.isPresent()){
            Event updatedEvent = optionalEvent.get();
            updatedEvent.setName(event.getName());
            updatedEvent.setDescription(event.getDescription());
            updatedEvent.setStartDate(event.getStartDate());
            updatedEvent.setEndDate(event.getEndDate());
             Event result = eventDao.save(updatedEvent);
             return result;
         }
         System.out.println("Error : The Event doesn't exist to update.");
         return null;
    }

    @Override
    public void delete(Event event) {
        eventDao.delete(event);
    }
}
