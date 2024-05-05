package com.esprit.resourcesmanagement.controllers;

import com.esprit.resourcesmanagement.entities.Resource;
//import com.esprit.resourcesmanagement.entities.Subscribe;
import com.esprit.resourcesmanagement.services.CategoryService;
import com.esprit.resourcesmanagement.services.ResourceService;
//import com.esprit.resourcesmanagement.services.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.mail.SimpleMailMessage;
//import javax.mail.internet.MimeMessage;


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
    @Autowired
    private CategoryService categoryService;
//    @Autowired
//    private SubscribeService subscribeService;
//    @Autowired
//    private JavaMailSender javaMailSender;

    @GetMapping("/all-resources")
    @ResponseBody
    public List<Resource> getAll() {
        return this.resourceService.getAllResources();
    }

    @GetMapping("/all-resources/{id}")
    @ResponseBody
    public List<Resource> getAllByUser(@PathVariable Long id) {
        return this.resourceService.getAllResourcesByUserId(id);
    }
    @GetMapping("/all-popular-resources")
    @ResponseBody
    public List<Resource> getAllPopular() {
        return this.resourceService.findTop4ResourcesByLikes();
    }

    @GetMapping("/getResourceById/{id}")
    @ResponseBody
    public Resource getResourceById(@PathVariable Long id) {

        this.resourceService.views(id);


        return id != null ? this.resourceService.findResourceById(id) : null;
    }
    @GetMapping("/getImageResource/{id}")
    @ResponseBody
    public byte[] getimage(@PathVariable Long id) {
        return id != null ? this.resourceService.findResourceById(id).getContent() : null;
    }

    @PutMapping("/updateResource/{id}")
    @ResponseBody
    public Resource updateResource(@RequestBody Resource resource,@PathVariable Long id) {
//        Resource resUp =this.resourceService.findResourceById(id);
//
//        List<Subscribe> subscribes =this.subscribeService.findByResource(id);
//        for (Subscribe subscribe :  subscribes){
//
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(subscribe.getEmail());
//            message.setSubject("Updated  Resource !!");
//            message.setText("Your favorite Resource" + resUp.getName() + " has been updated by the owner, check the update !!" );
//            this.javaMailSender.send(message);
//
//
//
//        }


     return resource != null ? this.resourceService.updateResource(resource,id) : null;

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
    public Resource addResource2(@RequestBody Resource resource, @RequestHeader("Authorization") String token) throws InterruptedException {
        try {
            Long userId = getUserIdFromUserService(token);
            if (userId != null) {
                resource.setUserId(userId);
            }

            resourceService.addResource(resource);
            Thread.sleep(100); // Pause pour simuler un traitement

            if (resource.getUrl().isEmpty()) {
                ajout(); // Méthode ajout() à implémenter
            }

            return resource;
        } catch (Exception e) {
            // Gérer l'exception ici
            e.printStackTrace(); // Pour le logging, vous pouvez modifier cette partie

            resourceService.addResource(resource);
            Thread.sleep(100); // Pause pour simuler un traitement

            if (resource.getUrl().isEmpty()) {
                ajout(); // Méthode ajout() à implémenter
            }

            return resource;// Ou renvoyer une réponse d'erreur appropriée
        }
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
            lastresource.setCategory(this.categoryService.findCategoryById(1L));
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
            lastresource.setCategory(this.categoryService.findCategoryById(1L));
            this.resourceService.deleteResource(beforeLastId);

        }







    }


    @DeleteMapping("/deleteResource/{id}")
    public void deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
    }

    @GetMapping("/like/{id}")
    @ResponseBody
    public void likeResource(@PathVariable Long id) {

        this.resourceService.like(id);

    }




    ///Récupération du user///////////////////

    public Long getUserIdFromUserService(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Long> response = restTemplate().exchange(
                "http://localhost:8082/api/v2/user/back/getUserSpot", HttpMethod.GET, entity, Long.class);

        return response.getBody();
    }

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }







}
