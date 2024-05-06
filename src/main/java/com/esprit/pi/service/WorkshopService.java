package com.esprit.pi.service;

import com.esprit.pi.entities.Workshop;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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
    public Workshop addWorkshop(@RequestParam("file") MultipartFile file, Workshop workshop) ;
    /**
     * Updates a given workshop fields.
     *
     * @param workshop
     * @param id
     * @return updated workshop object.
     */
    Workshop updateWorkshop(Workshop workshop, Long id);
    public  void deleteWorkshop(Long id);

    Workshop WorkshopById(Long id);

    int calculateAvailableCapacity(Long id);

    long calculateHoursSinceCreation(Workshop workshop);

    long countReservationsForWorkshop(Long id);
}
