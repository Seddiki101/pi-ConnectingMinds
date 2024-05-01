package com.esprit.resourcesmanagement.controllers;

import com.esprit.resourcesmanagement.entities.Resource;
import com.esprit.resourcesmanagement.services.ResourceService;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Rest Controller of Resource operations.
 */
@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/all-resources")
    @ResponseBody
    public List<Resource> getAll() {
        return this.resourceService.getAllResources();
    }
    @GetMapping("/all-popular-resources")
    @ResponseBody
    public List<Resource> getAllPopular() {
        return this.resourceService.findTop4ResourcesByLikes();
    }

    @GetMapping("/getResourceById/{id}")
    @ResponseBody
    public Resource getResourceById(@PathVariable Long id) {
        return id != null ? this.resourceService.findResourceById(id) : null;
    }
    @GetMapping("/getImageResource/{id}")
    @ResponseBody
    public byte[] getimage(@PathVariable Long id) {
        return id != null ? this.resourceService.findResourceById(id).getContent() : null;
    }

    @PutMapping("/updateResource")
    @ResponseBody
    public Resource updateResource(@RequestBody Resource resource) {
        return resource != null ? this.resourceService.updateResource(resource) : null;
    }

    @PostMapping("/addResource")
    @ResponseBody
    public byte[] addResource(@RequestParam("content") MultipartFile content) throws IOException {
        Resource resource = new Resource();
        if(content != null){  resource.setContent(content.getBytes());
            resource.setContentType(content.getContentType());
            resource.setSize(content.getSize());
            resourceService.addResource(resource);
        }


        return resource.getContent();
    }
    @PostMapping("/addResource2")
    @ResponseBody
    public Resource addResource2(@RequestBody Resource resource) throws IOException, InterruptedException {

        resourceService.addResource(resource);
        sleep(0,1);
        if(resource.getUrl().isEmpty()){this.ajout();}



        return resource;

    }

    public void ajout () throws InterruptedException {
        sleep(0,1);
        Resource lastresource= this.resourceService.getLastAddedResource();


        Long beforeLastId =lastresource.getResourceId()-1;

        Resource beforeLastResource =this.resourceService.findResourceById(beforeLastId);
        if (lastresource.getContent()==null){
            System.out.println("dans if ajout ");
            Date date = new Date(); // Obtenez la date actuelle
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Spécifiez le format de date

            // Formatez la date selon le format spécifié
            String formattedDate = dateFormat.format(date);
            lastresource.setDateCreation(formattedDate);
            lastresource.setContent(beforeLastResource.getContent());
            lastresource.setContentType(beforeLastResource.getContentType());
            lastresource.setUserId(beforeLastResource.getUserId());
            this.resourceService.deleteResource(beforeLastId);

        }

        else {
            System.out.println("dans else ajout ");
            Date date = new Date(); // Obtenez la date actuelle
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Spécifiez le format de date

            // Formatez la date selon le format spécifié
            String formattedDate = dateFormat.format(date);
            lastresource.setDateCreation(formattedDate);
            lastresource.setName(beforeLastResource.getName());
            lastresource.setDescription(beforeLastResource.getDescription());
            lastresource.setUserId(beforeLastResource.getUserId());
            this.resourceService.deleteResource(beforeLastId);

        }







    }


    @DeleteMapping("/deleteResource/{id}")
    public void deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
    }

}
