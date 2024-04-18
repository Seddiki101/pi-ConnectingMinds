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
    public Event create(Long groupId,Event event) {
        Optional<Team> optionalGroup = teamDao.findById(groupId);
        if(optionalGroup.isPresent()){
            Team team = optionalGroup.get();
            event.setTeam(team);
            Event result = eventDao.save(event);
            team.getEvents().add(event);
            teamDao.save(team);
            return result;
        }
        System.out.println("Error : Group doesn't exist.");
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
            Optional<Team> optionalGroup = teamDao.findById(updatedEvent.getTeam().getGroupId());
            if(optionalGroup.isPresent()){
                Event result = eventDao.save(updatedEvent);
                Team team = optionalGroup.get();
                team.getEvents().remove(optionalEvent.get());
                team.getEvents().add(updatedEvent);
                teamDao.save(team);
                return result;
            }
         }
         System.out.println("Error : The Event doesn't exist to update.");
         return null;
    }

    @Override
    public void delete(Event event) {
        Optional<Team> optionalGroup = teamDao.findById(event.getTeam().getGroupId());
        if(optionalGroup.isPresent()){
            Team team = optionalGroup.get();
            team.getEvents().remove(event);
            teamDao.save(team);
        }
        eventDao.delete(event);
    }
}
