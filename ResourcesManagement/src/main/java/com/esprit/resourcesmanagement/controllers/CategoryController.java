package com.esprit.resourcesmanagement.controllers;

import com.esprit.resourcesmanagement.entities.Category;
import com.esprit.resourcesmanagement.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller of Category operations.
 */
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all-categories")
    @ResponseBody
    public List<Category> getAll() {
        return this.categoryService.getAllCategories();
    }

    @GetMapping("/getCategoryById/{id}")
    @ResponseBody
    public Category getCategoryById(@PathVariable Long id) {
        return id != null ? this.categoryService.findCategoryById(id) : null;
    }

    @PutMapping("/updateCategory")
    @ResponseBody
    public Category updateCategory(@RequestBody Category category) {
        return category != null ? this.categoryService.updateCategory(category) : null;
    }

    @PostMapping("/addCategory")
    @ResponseBody
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/get-image/{categoryId}")
    @ResponseBody
    public byte[] getImageUrl(@PathVariable Long categoryId) {
     return this.categoryService.findCategoryById(categoryId).getImage();

    }

}
