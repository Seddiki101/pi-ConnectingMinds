package com.esprit.pi.controller;

import com.esprit.pi.entities.Reservation;
import com.esprit.pi.entities.Workshop;
import com.esprit.pi.service.WorkshopService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Rest Controller of workshop operations.
 */
@RestController
@AllArgsConstructor
@RequestMapping("workshops")
@CrossOrigin
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @GetMapping("get-all")
    @ResponseBody
    public List<Workshop> getAllworkshops(){
        return workshopService.findAll();
    }

    @GetMapping("get/{id}")
    @ResponseBody
    public Workshop getWorkshopById(@PathVariable("id") Long id ){
        return id != null ? this.workshopService.findById(id) : null;

    }

    @GetMapping("/Workshop/{id}/image")
    public ResponseEntity<byte[]> getImageForWorkshop(@PathVariable Long id) {
        Optional<Workshop> optionalWorkshop = Optional.ofNullable(workshopService.WorkshopById(id));
        if (optionalWorkshop.isPresent()) {
            Workshop workshop = optionalWorkshop.get();
            if (workshop.getImage() != null && workshop.getImage().length > 0) {
                // Renvoyer l'image sous forme de réponse avec le type de contenu approprié
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(workshop.getImage());
            } else {
                // Renvoyer une réponse indiquant que l'image n'est pas disponible pour ce post
                return ResponseEntity.notFound().build();
            }
        } else {
            // Renvoyer une réponse indiquant que le post n'existe pas
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("add")
    @ResponseBody
    public Workshop addWorkshop(@RequestParam("file") MultipartFile file,
                               Workshop workshop) {
        try {
            byte[] imageData = file.getBytes();
            workshop.setImage(imageData);
            return workshopService.addWorkshop(file, workshop);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @PutMapping("update/{id}")
    public ResponseEntity<Workshop> updateWorkshop(@PathVariable("id") Long id,
                                                   @RequestBody Workshop workshop) {

        Workshop result = workshopService.updateWorkshop(workshop, id);
        return   result!= null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteWorkshop(@PathVariable Long id){
        workshopService.deleteWorkshop(id);
    }

}
