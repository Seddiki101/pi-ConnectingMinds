package com.esprit.pi.serviceImpl;

import com.esprit.pi.daos.ReservationDaos;
import com.esprit.pi.daos.WorkshopDaos;
import com.esprit.pi.entities.Workshop;
import com.esprit.pi.service.WorkshopService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Implementation class of Workshop services.
 */
@Service
public class WorkshopServiceImpl implements WorkshopService {
    /**
     * Internal Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(WorkshopServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a workshop with id : %s";

    private static final String ERROR_UPDATE = "Error occured while updating";


    @Resource
    private WorkshopDaos workshopDaos;
    private ReservationDaos reservationDaos;


    @Override
    public List<Workshop> findAll() {
        return workshopDaos.findAll();
    }

    @Override
    public Workshop findById(Long id) {
        Workshop workshop= null;
        if ( id!= null) {
            final Optional<Workshop> optionalWorkshop = this.workshopDaos.findById(id);
            if (optionalWorkshop.isPresent()) {
                workshop = optionalWorkshop.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        }
        else {
            LOG.error(ERROR_NULL_ID);
        }
        return workshop;
    }

    @Override
    public Workshop addWorkshop(@RequestParam("file") MultipartFile file, Workshop workshop) {

        return this.workshopDaos.save(workshop);
    }


    @Override
    public Workshop updateWorkshop(Workshop workshop, Long id) {
        Optional<Workshop>optionalWorkshop = workshopDaos.findById(id);
        if(optionalWorkshop.isPresent()){
            Workshop updatedWorkshop = optionalWorkshop.get();
            updatedWorkshop.setTitle(workshop.getTitle());
            updatedWorkshop.setDescription(workshop.getDescription());
            updatedWorkshop.setDateDeb(workshop.getDateDeb());
            updatedWorkshop.setDateFin(workshop.getDateFin());
            updatedWorkshop.setPrix(workshop.getPrix());
            updatedWorkshop.setMaxCapacity(workshop.getMaxCapacity());
            updatedWorkshop.setCurrentCapacity(workshop.getCurrentCapacity());
            updatedWorkshop.setLocalisation(workshop.getLocalisation());

            Workshop result = workshopDaos.save(updatedWorkshop);
            System.out.println("updated");
            return result;
        }
        System.out.println("Workshop doesn't exist!!");

        return null;
    }

    @Override
    public void deleteWorkshop(Long id) {
        workshopDaos.deleteById(id);
    }

    @Override
    public Workshop WorkshopById(Long id) {
        Optional<Workshop> optionalWorkshop = workshopDaos.findById(id);
        return optionalWorkshop.orElse(null);
    }

    public int calculateAvailableCapacity(Long workshopId) {
        Optional<Workshop> workshopOptional = workshopDaos.findById(workshopId);
        if (!workshopOptional.isPresent()) {
            throw new NoSuchElementException("Workshop not found with id: " + workshopId);
        }
        Workshop workshop = workshopOptional.get();
        long reservedSpots = workshopDaos.countReservationsByWorkshopId(workshopId);
        return workshop.getMaxCapacity() - (int) reservedSpots;
    }


    public long calculateHoursSinceCreation(Workshop workshop) {
        if (workshop == null || workshop.getDateCreation() == null) {
            throw new IllegalArgumentException("Workshop or creation date is null");
        }
        Date now = new Date();
        long duration = now.getTime() - workshop.getDateCreation().getTime();
        return TimeUnit.MILLISECONDS.toHours(duration);
    }

    @Override
    public long countReservationsForWorkshop(Long workshopId) {
        return workshopDaos.countByWorkshopId(workshopId);
    }


}
