package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.EventDao;
import com.tn.esprit.kanbanboard.dao.GroupDao;
import com.tn.esprit.kanbanboard.entity.Event;
import com.tn.esprit.kanbanboard.entity.Group;
import com.tn.esprit.kanbanboard.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final GroupDao groupDao;
    @Autowired
    public EventServiceImpl(EventDao eventDao,GroupDao groupDao) {
        this.eventDao = eventDao;
        this.groupDao = groupDao;
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
        Optional<Group> optionalGroup = groupDao.findById(groupId);
        if(optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            event.setGroup(group);
            Event result = eventDao.save(event);
            group.getEvents().add(event);
            groupDao.save(group);
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
            Optional<Group> optionalGroup = groupDao.findById(updatedEvent.getGroup().getGroupId());
            if(optionalGroup.isPresent()){
                Event result = eventDao.save(updatedEvent);
                Group group = optionalGroup.get();
                group.getEvents().remove(optionalEvent.get());
                group.getEvents().add(updatedEvent);
                groupDao.save(group);
                return result;
            }
         }
         System.out.println("Error : The Event doesn't exist to update.");
         return null;
    }

    @Override
    public void delete(Event event) {
        Optional<Group> optionalGroup = groupDao.findById(event.getGroup().getGroupId());
        if(optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            group.getEvents().remove(event);
            groupDao.save(group);
        }
        eventDao.delete(event);
    }
}
