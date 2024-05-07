package com.esprit.resourcesmanagement.services;

import com.esprit.resourcesmanagement.entities.Category;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Category Service Manipulations.
 */
public interface CategoryService {
    /**
     * Fetch the List of categories.
     *
     * @return list of Category.
     */
    List<Category> getAllCategories();

    /**
     * Fetch Category by a given id.
     *
     * @param id
     * @return Category
     */
    Category findCategoryById(Long id);

    /**
     * Updates a given  category fields.
     *
     * @param category
     * @return updated  category object.
     */
    Category updateCategory (Category category);

    Category addCategory(@RequestBody Category category);

    void deleteCategory(Long id);
}
