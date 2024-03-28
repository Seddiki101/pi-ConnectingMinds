package com.esprit.pi.service;

import com.esprit.pi.entities.Workshop;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
/**
 * Workshop Service Interface.
 */
@Service
public interface WorkshopService {
    /**
     * Fetch the List of workshops.
     *
     * @return list of Workshop.
     */
    public  List<Workshop> findAll();
    /**
     * Fetch Workshop by a given Id.
     *
     * @param id
     * @return Workshop
     */
    public Workshop findById(Long id);
    public Workshop addWorkshop(@RequestBody Workshop workshop);
    /**
     * Updates a given workshop fields.
     *
     * @param workshop
     * @return updated workshop object.
     */
    Workshop updateWorkshop(@RequestBody Workshop workshop);

    public  void deleteWorkshop(Long id);
}
