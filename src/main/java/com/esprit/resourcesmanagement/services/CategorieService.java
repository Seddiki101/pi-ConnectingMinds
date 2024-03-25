package com.esprit.resourcesmanagement.services;

import com.esprit.resourcesmanagement.entities.Categorie;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Categorie Service Manipulations.
 */
public interface CategorieService {
    /**
     * Fetch the List of categories.
     *
     * @return list of Categorie.
     */
    List<Categorie> getAllCategories();

    /**
     * Fetch Categorie by a given id.
     *
     * @param id
     * @return Categorie
     */
    Categorie findCategorieById(Long id);

    /**
     * Updates a given  categorie fields.
     *
     * @param categorie
     * @return updated  categorie object.
     */
    Categorie updateCategorie ( Categorie  categorie);

    Categorie  addCategorie(@RequestBody Categorie  categorie);

    void deleteCategorie(Long id);
}
