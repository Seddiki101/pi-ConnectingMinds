package com.esprit.resourcesmanagement.controllers;

import com.esprit.resourcesmanagement.entities.Resource;
import com.esprit.resourcesmanagement.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getResourceById/{id}")
    @ResponseBody
    public Resource getResourceById(@PathVariable Long id) {
        return id != null ? this.resourceService.findResourceById(id) : null;
    }

    @PutMapping("/updateResource")
    @ResponseBody
    public Resource updateResource(@RequestBody Resource resource) {
        return resource != null ? this.resourceService.updateResource(resource) : null;
    }

    @PostMapping("/addResource")
    @ResponseBody
    public Resource addResource(@RequestBody Resource resource) {
        return resourceService.addResource(resource);
    }

    @DeleteMapping("/deleteResource/{id}")
    public void deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
    }

}
