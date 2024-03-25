package com.esprit.resourcesmanagement.controllers;

import com.esprit.resourcesmanagement.entities.Categorie;
import com.esprit.resourcesmanagement.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller of Categorie operations.
 */
@Controller
public class CategorieController {
    @Autowired
    private CategorieService categorieService;

    @GetMapping("/all-categories")
    @ResponseBody
    public List<Categorie> getAll() {
        return this.categorieService.getAllCategories();
    }

    @GetMapping("/getCategorieById/{id}")
    @ResponseBody
    public Categorie getCategorieById(@PathVariable Long id) {
        return id != null ? this.categorieService.findCategorieById(id) : null;
    }

    @PutMapping("/updateCategorie")
    @ResponseBody
    public Categorie updateCategorie(@RequestBody Categorie categorie) {
        return categorie != null ? this.categorieService.updateCategorie(categorie) : null;
    }

    @PostMapping("/addCategorie")
    @ResponseBody
    public Categorie addCategorie(@RequestBody Categorie categorie) {
        return categorieService.addCategorie(categorie);
    }

    @DeleteMapping("/deleteCategorie/{id}")
    public void deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
    }

}
