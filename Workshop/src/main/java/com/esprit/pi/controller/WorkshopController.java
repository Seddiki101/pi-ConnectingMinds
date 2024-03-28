package com.esprit.pi.controller;

import com.esprit.pi.entities.Workshop;
import com.esprit.pi.service.WorkshopService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest Controller of workshop operations.
 */
@RestController
@AllArgsConstructor
@RequestMapping("workshops")
@Controller
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @GetMapping("get-all")
    public List<Workshop> getAllworkshops(){
        return workshopService.findAll();
    }

    @GetMapping("get/{id}")
    public Workshop getWorkshopById(@PathVariable("id") Long id ){
        return id != null ? this.workshopService.findById(id) : null;

    }

    @PostMapping("add")
    public Workshop addWorkshop(@RequestBody Workshop workshop){
        return workshopService.addWorkshop(workshop);
    }

    @PutMapping("/update")
    public Workshop updateWorkshop(@RequestBody Workshop workshop){
        return workshop != null ? this.workshopService.updateWorkshop(workshop) : null;

    }

    @DeleteMapping("/delete/{id}")
    public void deleteWorkshop(@PathVariable Long id){
        workshopService.deleteWorkshop(id);
    }

}
