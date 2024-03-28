package com.esprit.pi.serviceImpl;

import com.esprit.pi.daos.WorkshopDaos;
import com.esprit.pi.entities.Workshop;
import com.esprit.pi.service.WorkshopService;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Workshop addWorkshop(Workshop workshop) {
        return this.workshopDaos.save(workshop);
    }

    @Override
    public Workshop updateWorkshop(Workshop workshop) {
        Workshop updateWorkshop=null;
        if (workshop!= null){
            updateWorkshop = this.workshopDaos.save(workshop);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return updateWorkshop;
    }

    @Override
    public void deleteWorkshop(Long id) {
        workshopDaos.deleteById(id);
    }
}
